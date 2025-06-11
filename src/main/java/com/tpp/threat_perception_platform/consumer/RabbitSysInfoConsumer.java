package com.tpp.threat_perception_platform.consumer;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;

import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.pojo.AppInfo;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccountInfoService;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.service.AppInfoService;
import com.tpp.threat_perception_platform.service.ProcessInfoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}