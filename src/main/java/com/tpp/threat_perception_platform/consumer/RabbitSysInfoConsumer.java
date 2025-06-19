package com.tpp.threat_perception_platform.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class RabbitSysInfoConsumer {
    @Autowired
    private HostService hostService;

    @Autowired
    private AppInfoService appInfoService;

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

    @RabbitListener(queues = "sysinfo_queue")
    public void receive(String message, @Headers Map<String,Object> headers,
                        Channel channel) throws IOException {
        System.out.println("Received message: " + message);
        try {
            // 将数据存储到数据库
            Host host = JSON.parseObject(message, Host.class);
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
            Host host = JSON.parseObject(message, Host.class);

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
    public void receiveAppInfo(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received AppInfo message: " + message);
        try {
            // 反序列化 JSON → AppInfo 对象
            List<AppInfo> appInfoList = JSON.parseArray(message, AppInfo.class);

            // 循环保存每一个 AppInfo
            for (AppInfo appInfo : appInfoList) {
                ResponseResult result = appInfoService.saveApp(appInfo);
                System.out.println("Save result: " + result.getMsg());
            }

            // 手动 ack
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process AppInfo message: " + message);

            // 即使出错，也 ack，避免消息积压
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }

    @RabbitListener(queues = "process_queue")
    public void receiveProcess(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received process info message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            List<ProcessInfo> processInfoList = JSON.parseArray(message, ProcessInfo.class);

            boolean allSuccess = true;
            for (ProcessInfo processInfo : processInfoList) {
                ResponseResult result = processInfoService.save(processInfo);
                if (result.getCode() != 0) {
                    allSuccess = false;
                    // 这里可以选择日志记录具体失败的 processInfo
                    System.err.println("Failed to save processInfo: " + processInfo);
                }
            }

            if (allSuccess) {
                channel.basicAck(deliveryTag, false);
            } else {
                // 部分失败，视业务是否重试，先丢弃消息不重回队列
                channel.basicNack(deliveryTag, false, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(deliveryTag, false, false);
        }
    }

    @RabbitListener(queues = "account_queue")
    public void receiveAccount(String message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        System.out.println("Received message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        // 简单规则库：只检测 name = guest，不区分大小写
        Map<String, List<String>> ruleMap = new HashMap<>();
        ruleMap.put("name", Arrays.asList("guest"));

        try {
            List<AccountInfo> accountList = JSON.parseArray(message, AccountInfo.class);
            boolean allSuccess = true;

            for (AccountInfo account : accountList) {
                boolean isHarmful = false;
                String harmfulKey = null;

                for (Map.Entry<String, List<String>> entry : ruleMap.entrySet()) {
                    String field = entry.getKey();
                    List<String> harmfulValues = entry.getValue();

                    try {
                        Field declaredField = AccountInfo.class.getDeclaredField(field);
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(account);

                        if (value != null && harmfulValues.stream().anyMatch(v -> v.equalsIgnoreCase(value.toString()))) {
                            isHarmful = true;
                            harmfulKey = field;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                account.setIsHarmful(isHarmful ? 1 : 0);
                account.setHarmfulKey(isHarmful ? harmfulKey : null);

                try {
                    int res = accountInfoService.analyzeAndSaveAccountInfo(account);
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
        System.out.println("Received message: " + message);

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG); // 提前获取 deliveryTag
        boolean isAcked = false;

        try {
            // 解析 JSON 数组
            JSONArray jsonArray = JSON.parseArray(message);
            if (jsonArray.isEmpty()) {
                throw new JSONException("Received empty JSON array");
            }

            // 提取 macAddress 和 services
            String macAddress = jsonArray.getJSONObject(0).getString("mac"); // 假设每个 JSON 对象都有 "mac" 字段
            List<ServiceInfo> services = jsonArray.toJavaList(ServiceInfo.class);

            System.out.println("macAddress: " + macAddress);
            System.out.println("services: " + services);

            serviceInfoService.saveService(macAddress, services);
            isAcked = true; // 标记消息已成功处理
        } catch (JSONException e) {
            System.err.println("JSONException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            if (!isAcked) {
                System.out.println("Message processing failed. Rejecting message with delivery tag: " + deliveryTag);
                channel.basicNack(deliveryTag, false, true); // 拒绝消息并重新入队
            } else {
                channel.basicAck(deliveryTag, false); // 确认消息
            }
        }
    }



    @RabbitListener(queues = "applicationRisk_queue")
    public void receiveAppRisk(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received ApplicationRiskParam list message: " + message);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        boolean allSuccess = true;

        try {
            // 解析消息为参数列表
            List<ApplicationRisk> paramList = JSON.parseArray(message, ApplicationRisk.class);

            for (ApplicationRisk param : paramList) {
                try {
                    // 转换并赋值检测时间
                    ApplicationRisk appRisk = new ApplicationRisk();
                    BeanUtils.copyProperties(param, appRisk);
                    appRisk.setDetectionTime(new Date());
                    appRisk.setMac(param.getMac());
                    // 保存到数据库
                    ResponseResult result = applicationRiskService.saveAppRisk(appRisk);
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
            List<SystemRisk> paramList = JSON.parseArray(message, SystemRisk.class);

            for (SystemRisk param : paramList) {
                try {
                    // 创建新对象并赋值检测时间
                    SystemRisk systemRisk = new SystemRisk();
                    BeanUtils.copyProperties(param, systemRisk);
                    systemRisk.setUpdatedAt(new Date());

                    // 保存到数据库
                    ResponseResult result = systemRiskService.saveSystemRisk(systemRisk);
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
            List<Hotfix> hotfixList = JSON.parseArray(message, Hotfix.class);

            // 循环保存每一个
            for (Hotfix hotfix : hotfixList) {
                ResponseResult result = hotfixService.saveHotfix(hotfix);
                System.out.println("Save result: " + result.getMsg());
                // test: 提取危险补丁并输出
                if (!hotfixList.isEmpty()) {
                    String mac = hotfix.getMac();
                    ResponseResult<List<DangerousHotfix>> response = hotfixService.getDangerousPatches(mac);
                    List<DangerousHotfix> dangerousList = response.getData();
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
            List<WeakpasswordRisk> weakpasswordRiskList = JSON.parseArray(message, WeakpasswordRisk.class);

            // 循环保存每一个
            for (WeakpasswordRisk weakpasswordRisk: weakpasswordRiskList) {
                ResponseResult result = weakpasswordRiskService.saveWeakpasswordRisk(weakpasswordRisk);
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

    @RabbitListener(queues = "vulnerability_queue")
    public void receiveVulnerability(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received vulnerability message: " + message);
        try {
            // 反序列化 JSON → 对象
            List<VulnerabilityRisk> vulnerabilityRiskList = JSON.parseArray(message, VulnerabilityRisk.class);

            // 循环保存每一个
            for (VulnerabilityRisk vulnerabilityRisk: vulnerabilityRiskList) {
                ResponseResult result = vulnerabilityService.saveVulnerabilityRisk(vulnerabilityRisk);
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
            LogParam param = JSON.parseObject(message, LogParam.class);

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
            List<LogParam> logParams = JSON.parseArray(message, LogParam.class);

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

}