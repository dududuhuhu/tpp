package com.tpp.threat_perception_platform.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.tpp.threat_perception_platform.pojo.Service;
import com.tpp.threat_perception_platform.service.ServiceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AssetsServiceConsumer {
    @Autowired
    private ServiceService serviceService;

    @RabbitListener(queues="assets_service_queue")
    public void receive(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("Received message: " + message);
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            List<Service> services = JSON.parseArray(jsonObject.getString("service"), Service.class);
            String macAddress = jsonObject.getString("macAddress");
            System.out.println("macAddress: " + macAddress);
            System.out.println("services: " + services);
            serviceService.saveService(macAddress, services);
        }
        catch (JSONException e) {
            System.err.println("JSONException: " + e.getMessage());

            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            // ACK
            channel.basicAck(deliveryTag, false);
        }
        // catch (Exception e) {
        //     System.err.println("Exception: " + e.getMessage());
        // }
        finally{
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        }
    }
}
