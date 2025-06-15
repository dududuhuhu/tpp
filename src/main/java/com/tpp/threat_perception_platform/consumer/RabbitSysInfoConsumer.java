package com.tpp.threat_perception_platform.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.*;
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
                    int res = accountInfoService.saveAccountInfo(account);
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

    /*
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
*/

}