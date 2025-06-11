package com.tpp.threat_perception_platform.consumer;

import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.pojo.Host;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SysInfoConsumer {
    @Autowired
    private HostService hostService;

    @RabbitListener(queues="sysinfo_queue")
    public void receive(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received Message: " + message);
        try{
            Host host = JSON.parseObject(message, Host.class);
            int res = hostService.saveHost(host);

            if (res > 0) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                // ACK
                channel.basicAck(deliveryTag, false);
            }
        }
        catch (JSONException e) {
            System.err.println("JSONException: " + e.getMessage());

            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            // ACK
            channel.basicAck(deliveryTag, false);
        }
        catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    @RabbitListener(queues="status_queue")
    public void receiveStatus(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received Message: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(message, Map.class);

            String macAddress = (String) data.get("macAddress");
            if (macAddress != null && !macAddress.isEmpty()) {
                hostService.updateStatus(macAddress);
            } else {
                System.err.println("Invalid or missing macAddress in message");
            }
        }
        catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        finally {
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }
}
