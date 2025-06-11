package com.tpp.threat_perception_platform.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface RabbitService {

    /**
     * 创建队列
     * @param exchange:交换机的名字
     * @param queue:队列的名字
     * @param routingKey:路由键
     */
    public  void createAgent(String exchange,String queue,String routingKey);

    void sendMessage(String exchange, String routingKey, String message);
}
