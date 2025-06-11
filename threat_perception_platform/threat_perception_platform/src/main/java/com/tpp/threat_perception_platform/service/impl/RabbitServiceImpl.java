package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitServiceImpl implements RabbitService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createAgentQueue(String exchangeName, String queue, String routingKey) {
        rabbitTemplate.execute(channel->{
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchangeName, routingKey);
            return null;
        });
    }

    @Override
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
