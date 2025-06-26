package com.tpp.threat_perception_platform.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.rabbitmq.client.Channel;

import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.LogRulesMapper;
import com.tpp.threat_perception_platform.param.AgentMessageParam;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import com.tpp.threat_perception_platform.utils.SignKeyPair;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@Component
public class RabbitSysInfoConsumer {
    @Autowired
    private HostService hostService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ProcessInfoService processInfoService;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private ServiceInfoService serviceInfoService;

    @Autowired
    private ApplicationRiskService applicationRiskService;


    @Autowired
    private SystemRiskService systemRiskService;

    @Autowired
    private HotfixService hotfixService;

    @Autowired
    private WeakpasswordRiskService weakpasswordRiskService;

    @Autowired
    private VulnerabilityService vulnerabilityService;

    @Autowired
    private AcctChgLogService acctChgLogService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private LoginActionService loginActionService;

    @Autowired
    private VerifierService verifierService;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private BaselineDetectService baselineDetectService;

    @Autowired
    private BaselineHardeningService baselineHardeningService;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private LogRulesMapper logRulesMapper;

    <T> T validateAndParseObject(String message, Class<T> clazz) {
        try {
            AgentMessageParam agentMessageParam = JSON.parseObject(message, AgentMessageParam.class);
            if (!agentMessageParam.check() || !verifierService.verifySign(agentMessageParam.getMac(), agentMessageParam.getMac() + agentMessageParam.getMessage(), agentMessageParam.getSig())) {
                return null;
            }

            return JSON.parseObject(agentMessageParam.getMessage(), clazz);
        }
        catch (Exception e) {
            return null;
        }
    }

    <T> List<T> validateAndParseList(String message, Class<T> clazz) {
        try {
            AgentMessageParam agentMessageParam = JSON.parseObject(message, AgentMessageParam.class);
            if (!agentMessageParam.check() || !verifierService.verifySign(agentMessageParam.getMac(), agentMessageParam.getMac() + agentMessageParam.getMessage(), agentMessageParam.getSig())) {
                return null;
            }

            return JSON.parseArray(agentMessageParam.getMessage(), clazz);
        }
        catch (Exception e) {
            return null;
        }
    }

    JSONArray validateAndParseJsonArray(String message) {
        try {
            AgentMessageParam agentMessageParam = JSON.parseObject(message, AgentMessageParam.class);
            if (!agentMessageParam.check() || !verifierService.verifySign(agentMessageParam.getMac(), agentMessageParam.getMac() + agentMessageParam.getMessage(), agentMessageParam.getSig())) {
                return null;
            }
            return JSON.parseArray(agentMessageParam.getMessage());
        }
        catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "sysinfo_queue")
    public void receive(String message, @Headers Map<String,Object> headers,
                        Channel channel) throws IOException {
        System.out.println("Received message: " + message);
        try {
            // 将数据存储到数据库
            AgentMessageParam agentMessageParam = JSON.parseObject(message, AgentMessageParam.class);

            ObjectMapper mapper = new ObjectMapper();
            Integer status = 0;
            try {
                if (!agentMessageParam.check()) return;
                SignKeyPair keyPair = new SignKeyPair();
                JsonNode node = mapper.readTree(agentMessageParam.getMessage());
                if (!node.has("pub")) return;
                String pemPublicKey = node.get("pub").asText();
                keyPair.loadPublicKey(pemPublicKey);
                byte[] _message = (agentMessageParam.getMac() + agentMessageParam.getMessage()).getBytes(StandardCharsets.UTF_8);
                byte[] _sig = SignKeyPair.translateStrToBytes(agentMessageParam.getSig());
                if (!keyPair.verify(_message, _sig)) return;
                else {
                    if (!verifierService.validateAndAddUserPemPublicKey(agentMessageParam.getMac(), pemPublicKey)) status = 0;
                    else status = 1;
                }
            }
            catch (JsonMappingException e) {
                status = 0;
            }
            catch (Exception e) {
                status = 0;
            }
            finally {
                if (status == 0) {
                    Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
                    // ACK
                    channel.basicAck(deliveryTag,false);                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            String ret_message = mapper.writeValueAsString(map);
            rabbitService.sendMessage("agent_" + agentMessageParam.getMac().replaceAll(":", "") + "_exchange", agentMessageParam.getMac().replaceAll(":", ""), ret_message);

            Host host = JSON.parseObject(agentMessageParam.getMessage(), Host.class);
            // 存储到数据库
            int res = hostService.saveHost(host);
            if (res > 0){
                // 手动 ACK, 先获取 deliveryTag
                Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
                // ACK
                channel.basicAck(deliveryTag,false);
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
            // 手动ACK
            Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            // ACK
            channel.basicNack(deliveryTag,false,true);
        }
    }

    @RabbitListener(queues = "status_queue")
    public void receiveStatus(String message, @Headers Map<String,Object> headers,
                              Channel channel) throws IOException {
        System.out.println("Received message: " + message);
        // 反序列化数据
        try {
            Host host = validateAndParseObject(message, Host.class);
            if (host == null) {
                Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag,false);
                return;
            }

            int res = hostService.updateHostByMacAddress(host);
            if (res > 0){
                // 手动 ACK, 先获取 deliveryTag
                Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
                // ACK
                channel.basicAck(deliveryTag,false);
            }
        } catch (IOException e) {
            // 手动 ACK, 先获取 deliveryTag
            Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            // ACK
            channel.basicAck(deliveryTag,false);
        }

    }

    /**
     * 监听队列 app_info_queue，自动处理消息
     */
    @RabbitListener(queues = "app_queue")
    public void receiveAppInfo(String message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        System.out.println("Received AppInfo message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // 反序列化 JSON → AppInfo 对象
            // List<AppInfo> appInfoList = JSON.parseArray(message, AppInfo.class);
            List<AppInfo> appInfoList = validateAndParseList(message, AppInfo.class);
            if (appInfoList == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }
            boolean allSuccess = true;

            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 循环保存每一个 AppInfo
            for (AppInfo appInfo : appInfoList) {
                // 设置默认风险状态
                appInfo.setIsHarmful(0);
                appInfo.setHarmfulKey(null);

                try {
                    int res = appInfoService.analyzeAndSaveAppInfo(appInfo,now);
                    if (res <= 0) {
                        allSuccess = false;
                        System.err.println("Failed to save appInfo: " + appInfo);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    e.printStackTrace();
                }
            }

            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("AppInfo message processed successfully and ACKed");
            } else {
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some appInfo failed to save, message NACKed and requeued");
            }

        } catch (Exception e) {
            System.err.println("Error processing appInfo message: " + e.getMessage());
            channel.basicNack(deliveryTag, false, true);
            throw e;
        }
    }

    @RabbitListener(queues = "process_queue")
    public void receiveProcess(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received process info message: " + message);
        System.out.println("Headers: " + headers);
        System.out.println("deliveryTag: " + headers.get(AmqpHeaders.DELIVERY_TAG));

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // List<ProcessInfo> processInfoList = JSON.parseArray(message, ProcessInfo.class);
            List<ProcessInfo> processInfoList = validateAndParseList(message, ProcessInfo.class);
            // 验证失败直接确认，返回
            if (processInfoList == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            boolean allSuccess = true;

            for (ProcessInfo processInfo : processInfoList) {
                // 设置默认风险状态
                processInfo.setIsHarmful(0);
                processInfo.setHarmfulKey(null);

                try {
                    int res = processInfoService.analyzeAndSaveProcessInfo(processInfo,now);
                    if (res <= 0) {
                        allSuccess = false;
                        System.err.println("Failed to save processInfo: " + processInfo);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    e.printStackTrace();
                }
            }

            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("ProcessInfo message processed successfully and ACKed");
            } else {
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some processInfo failed to save, message NACKed and requeued");
            }

        } catch (Exception e) {
            System.err.println("Error processing processInfo message: " + e.getMessage());
            channel.basicNack(deliveryTag, false, true);
            throw e;
        }
    }


    @RabbitListener(queues = "account_queue")
    public void receiveAccount(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // List<AccountInfo> accountList = JSON.parseArray(message, AccountInfo.class);
            List<AccountInfo> accountList = validateAndParseList(message, AccountInfo.class);
            if (accountList == null) {
                channel.basicAck(deliveryTag, false);
                System.out.println("账号序列为空！");
                return;
            }
            boolean allSuccess = true;

            // 设置创建时间、更新时间
            Date now = new Date();

            for (AccountInfo account : accountList) {
//                // 直接设置isHarmful为false
//                account.setIsHarmful(0); // 假设isHarmful字段是int类型，0表示false
//                account.setHarmfulKey(null); // 清空harmfulKey字段

                try {
                    // AI辅助判断账户风险性
                    int res = accountInfoService.analyzeAndSaveAccountInfo(account,now);
                    if (res <= 0) {
                        allSuccess = false;
                        System.err.println("Failed to save account: " + account);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    e.printStackTrace();
                }
            }

            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("Message processed successfully and ACKed");
            } else {
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some accounts failed to save, message NACKed and requeued");
            }

        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            channel.basicNack(deliveryTag, false, true);
            throw e;
        }
    }

    @RabbitListener(queues = "service_queue")
    public void receiveService(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received service message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            List<ServiceInfo> serviceList = validateAndParseList(message, ServiceInfo.class);
            if (serviceList == null) {
                channel.basicAck(deliveryTag, false);
                System.out.println("签名验证失败！");
                return;
            }

            boolean allSuccess = true;

            // 设置创建时间、更新时间
            Date now = new Date();
            for (ServiceInfo service : serviceList) {
                try {
                    int res = serviceInfoService.analyzeAndSaveServiceInfo(service, now);
                    if (res <= 0) {
                        allSuccess = false;
                        System.err.println("Failed to save service: " + service);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    e.printStackTrace();
                }
            }

            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("Service message processed successfully and ACKed");
            } else {
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some services failed to save, message NACKed and requeued");
            }

        } catch (Exception e) {
            System.err.println("Error processing service message: " + e.getMessage());
            channel.basicNack(deliveryTag, false, true);
            throw e;
        }
    }



    @RabbitListener(queues = "applicationRisk_queue")
    public void receiveAppRisk(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received ApplicationRiskParam list message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        boolean allSuccess = true;

        try {
            // 解析消息为参数列表
            // List<ApplicationRisk> paramList = JSON.parseArray(message, ApplicationRisk.class);
            List<ApplicationRisk> paramList = validateAndParseList(message, ApplicationRisk.class);
            if (paramList == null) {
                return;
            }
            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (ApplicationRisk param : paramList) {
                try {
                    // 转换并赋值检测时间
                    ApplicationRisk appRisk = new ApplicationRisk();
                    BeanUtils.copyProperties(param, appRisk);
                    appRisk.setDetectionTime(new Date());
                    appRisk.setMac(param.getMac());
                    // 保存到数据库
                    ResponseResult result = applicationRiskService.saveAppRisk(appRisk, now);
                    System.out.printf("Risk detection result for param [%s]: code=%d, msg=%s%n",
                            param, result.getCode(), result.getMsg());

                    if (result.getCode() != 0) {
                        allSuccess = false;
                        System.err.printf("Failed to save risk info for param: %s%n", param);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    System.err.printf("Exception while saving risk info for param %s:%n", param);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            allSuccess = false;
            System.err.println("Exception while processing risk message:");
            e.printStackTrace();
        } finally {
            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("All risk messages processed successfully, ACKed.");
            } else {
                // 出错时决定是否重试，这里设为重试
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some risk messages failed, message NACKed and requeued.");
            }
        }
    }

    @RabbitListener(queues = "systemRisk_queue")
    public void receiveSystemRisk(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received SystemRisk list message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        boolean allSuccess = true;

        try {
            // 解析消息为参数列表
            // List<SystemRisk> paramList = JSON.parseArray(message, SystemRisk.class);
            List<SystemRisk> paramList = validateAndParseList(message, SystemRisk.class);
            if (paramList == null) return;

            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (SystemRisk param : paramList) {
                try {
                    // 创建新对象并赋值检测时间
                    SystemRisk systemRisk = new SystemRisk();
                    BeanUtils.copyProperties(param, systemRisk);
                    systemRisk.setUpdatedAt(new Date());

                    // 保存到数据库
                    ResponseResult result = systemRiskService.saveSystemRisk(systemRisk, now);
                    System.out.printf("Risk detection result for param [%s]: code=%d, msg=%s%n",
                            param, result.getCode(), result.getMsg());

                    if (result.getCode() != 0) {
                        allSuccess = false;
                        System.err.printf("Failed to save system risk info for param: %s%n", param);
                    }
                } catch (Exception e) {
                    allSuccess = false;
                    System.err.printf("Exception while saving system risk info for param %s:%n", param);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            allSuccess = false;
            System.err.println("Exception while processing system risk message:");
            e.printStackTrace();
        } finally {
            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
                System.out.println("All system risk messages processed successfully, ACKed.");
            } else {
                // 出错时决定是否重试，这里设为重试
                channel.basicNack(deliveryTag, false, true);
                System.err.println("Some system risk messages failed, message NACKed and requeued.");
            }
        }
    }


    @RabbitListener(queues = "hotfix_queue")
    public void receiveHotfix(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received hotfix message: " + message);
        try {
            // 反序列化 JSON → 对象
            // List<Hotfix> hotfixList = JSON.parseArray(message, Hotfix.class);
            List<Hotfix> hotfixList = validateAndParseList(message, Hotfix.class);
            if (hotfixList == null) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag, false);
                return;
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 循环保存每一个
            for (Hotfix hotfix : hotfixList) {
                ResponseResult result = hotfixService.saveHotfix(hotfix,now);
                System.out.println("Save result: " + result.getMsg());
                // test: 提取危险补丁并输出
                if (!hotfixList.isEmpty()) {
                    String mac = hotfix.getMac();
                    HotfixParam param = new HotfixParam();
                    param.setMacAddress(mac);
//                    ResponseResult<List<DangerousHotfix>> response = hotfixService.getDangerousPatches(param);
//                    List<DangerousHotfix> dangerousList = response.getData();
                } else {
                    System.out.println("未收到任何 Hotfix 数据，跳过危险补丁检测");
                }
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process hotfix message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "password_queue")
    public void receiveWeakpassword(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received weakpassword message: " + message);
        try {
            // 反序列化 JSON → 对象
            // List<WeakpasswordRisk> weakpasswordRiskList = JSON.parseArray(message, WeakpasswordRisk.class);
            List<WeakpasswordRisk> weakpasswordRiskList = validateAndParseList(message, WeakpasswordRisk.class);
            if (weakpasswordRiskList == null) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag, false);
                return;
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 循环保存每一个
            for (WeakpasswordRisk weakpasswordRisk: weakpasswordRiskList) {
                ResponseResult result = weakpasswordRiskService.saveWeakpasswordRisk(weakpasswordRisk, now);
                System.out.println("Save weakpasswordsRisk result: " + result.getMsg());
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process hotfix message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "vulnerability_queue")
    public void receiveVulnerability(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received vulnerability message: " + message);
        try {
            // 反序列化 JSON → 对象
            // List<VulnerabilityRisk> vulnerabilityRiskList = JSON.parseArray(message, VulnerabilityRisk.class);
            List<VulnerabilityRisk> vulnerabilityRiskList = validateAndParseList(message, VulnerabilityRisk.class);
            if (vulnerabilityRiskList == null) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag, false);
                return;
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 循环保存每一个
            for (VulnerabilityRisk vulnerabilityRisk: vulnerabilityRiskList) {
                vulnerabilityRisk.setIsExit(1);
                ResponseResult result = vulnerabilityService.saveVulnerabilityRisk(vulnerabilityRisk, now);
                System.out.println("Save result: " + result.getMsg());
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process hotfix message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "accountChangeLog_queue")
    public void receiveAccountChange(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received account change message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // 反序列化 JSON 到 LogParam 对象（包含 mac 和 actions）
            // LogParam param = JSON.parseObject(message, LogParam.class);
            LogParam param = validateAndParseObject(message, LogParam.class);
            if (param == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }

            if (param.getMac() == null || param.getActions() == null || param.getActions().isEmpty()) {
                System.err.println("Invalid account change message: missing mac or actions");
                channel.basicAck(deliveryTag, false);
                return;
            }

            // 调用业务服务保存
            Integer savedCount = acctChgLogService.saveAcctChgLog(param.getMac(), param.getActions());
            System.out.println("Saved " + savedCount + " account change logs for mac: " + param.getMac());

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process account change message: " + message);

            // 即使失败，手动 ack 防止消息积压（根据业务需求可改为重试或死信）
            channel.basicAck(deliveryTag, false);
        }
    }


    // 两个队列还是分开
    @RabbitListener(queues = "auditLog_queue")
    public void receiveAuditLog(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received auditLog message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // List<LogParam> logParams = JSON.parseArray(message, LogParam.class);
            List<LogParam> logParams = validateAndParseList(message, LogParam.class);
            if (logParams == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }

            for (LogParam param : logParams) {
                // 构造 LoginLog
                LoginLog log = new LoginLog();
                log.setMac(param.getMac());
                log.setUsername(param.getUsername());
                log.setLoginTime(param.getLoginTime());
                log.setLogoffTime(param.getLogoffTime());
                // log.setIsRiskUser(param.getIsRiskUser());
                // log.setIsRiskTime(param.getIsRiskTime());

                // 保存 login_log 并获取主键
                loginLogService.saveLoginLog(log);
                int logId = log.getId();

                // 循环保存 login_action
                for (LogParam.Action action : param.getActions()) {
                    LoginAction loginAction = new LoginAction();
                    loginAction.setLoginLogId(logId);
                    loginAction.setEventId(action.getEventId());
                    loginAction.setTimestamp(action.getTimestamp());
                    loginAction.setAction(action.getAction());
                    loginAction.setDetails(action.getDetails());

                    loginActionService.saveLoginAction(loginAction);
                }
            }

            // 消息处理成功，ACK
            channel.basicAck(deliveryTag, false);

            // test
            // 调用获取所有登录日志带动作的方法
            List<LogParam> allLogs = loginActionService.getLoginLogsWithActions(null);

            // 转成JSON字符串打印（用fastjson）
            String logsJson = JSON.toJSONString(allLogs, true);  // 第二个参数true表示格式化输出
            System.out.println("当前数据库中所有登录日志及动作：\n" + logsJson);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process login logs: " + message);
            // 出错也 ack，避免消息堆积
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "loginLog_queue")
    public void receiveLoginLog(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received loginLog message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            List<LogParam> logParams = JSON.parseArray(message, LogParam.class);

            for (LogParam param : logParams) {
                // 构造 LoginLog
                LoginLog log = new LoginLog();
                log.setMac(param.getMac());
                log.setUsername(param.getUsername());
                log.setLoginTime(param.getLoginTime());
                // log.setLogoffTime(param.getLogoffTime());
                log.setIsRiskUser(param.getIsRiskUser());
                log.setIsRiskTime(param.getIsRiskTime());

                // 保存 login_log 并获取主键
                loginLogService.saveLoginLog(log);
            }

            // 消息处理成功，ACK
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process login logs: " + message);
            // 出错也 ack，避免消息堆积
            channel.basicAck(deliveryTag, false);
        }
    }

    // 基线检查
    @RabbitListener(queues = "baselineDetect_queue")
    public void receiveBaselineDetect(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received BaselineDetect message: " + message);
        try {
            // 反序列化 JSON → 对象
            // List<VulnerabilityRisk> baselineDetectList = JSON.parseArray(message, VulnerabilityRisk.class);
            List<BaselineDetect> baselineDetectList = JSON.parseArray(message, BaselineDetect.class);
            if (baselineDetectList == null) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag, false);
                return;
            }

            // 循环保存每一个
            for (BaselineDetect baselineDetect: baselineDetectList) {
                ResponseResult result = baselineDetectService.saveBaselineDetect(baselineDetect);
                System.out.println("Save result: " + result.getMsg());
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);

            // test
            // ✅ 调用 baselineDetectList 展示分页列表
            // 假设我们取第一条数据的 mac 作为参数（实际也可从原始 param 中构造）
            if (!baselineDetectList.isEmpty()) {
                String mac = baselineDetectList.get(0).getMac();

                // 构造分页查询参数对象
                BaselineDetectParam param = new BaselineDetectParam();
                param.setMac(mac);
                param.setPage(1);   // 默认第一页
                param.setLimit(10); // 默认每页10条，可根据需要设置

                // 调用服务层查询方法
                ResponseResult pageResult = baselineDetectService.baselineDetectList(param);

                // 打印分页结果（实际可返回给前端或日志系统）
                System.out.println("分页查询结果：" + pageResult.getData());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process hotfix message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "baselineHardening_queue")
    public void receiveBaselineHardening(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received BaselineHardening message: " + message);
        try {
            // 反序列化 JSON → 对象
            // List<VulnerabilityRisk> baselineDetectList = JSON.parseArray(message, VulnerabilityRisk.class);
            List<BaselineHardening> baselineHardeningList = JSON.parseArray(message, BaselineHardening.class);
            if (baselineHardeningList == null) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                channel.basicAck(deliveryTag, false);
                return;
            }

            // 循环保存每一个
            for (BaselineHardening baselineHardening: baselineHardeningList) {
                ResponseResult result = baselineHardeningService.saveBaselineHardening(baselineHardening);
                System.out.println("Save result: " + result.getMsg());
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);

            // test
            // 假设我们取第一条数据的 mac 作为参数（实际也可从原始 param 中构造）
            if (!baselineHardeningList.isEmpty()) {
                String mac = baselineHardeningList.get(0).getMac();

                // 构造分页查询参数对象
                BaselineDetectParam param = new BaselineDetectParam();
                param.setMac(mac);
                param.setPage(1);   // 默认第一页
                param.setLimit(10); // 默认每页10条，可根据需要设置

                // 调用服务层查询方法
                ResponseResult pageResult = baselineHardeningService.baselineHardeningList(param);

                // 打印分页结果（实际可返回给前端或日志系统）
                System.out.println("分页查询结果：" + pageResult.getData());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process hotfix message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    public static class InTimeRequest {
        private String mac;
        public InTimeRequest() {}

        public String getMac() {
            return mac;
        }
        public void setMac(String mac) {
            this.mac = mac;
        }
    }

    @RabbitListener(queues = "inTimeRequest_queue")
    public void receiveInTimeRequest(String message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        System.out.println("接收到的消息: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            InTimeRequest inTimeRequest = validateAndParseObject(message, InTimeRequest.class);
            if (inTimeRequest == null) {
                channel.basicAck(deliveryTag, false);
                return;
            }
            String mac = inTimeRequest.getMac();

            System.out.println("提取到的 MAC: " + mac);

            // 查询主机信息
            Host db_host = hostMapper.selectByMacAddress(mac);
            if (db_host == null) {
                System.out.println("数据库中未找到主机信息，MAC: " + mac + "，丢弃消息");
                channel.basicAck(deliveryTag, false);
                return;
            }

            String platform = db_host.getOsType();
            System.out.println("获取到主机平台类型: " + platform);

            // 发送日志规则
            ruleService.sendLogRules(mac, platform);
            System.out.println("已向指定队列发送日志规则");

            // 正常 ACK 消息
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            System.err.println("处理实时请求消息异常: " + e.getMessage());
            e.printStackTrace();
            // 即使异常也 ACK，避免消息堆积
            channel.basicAck(deliveryTag, false);
        }
    }
}