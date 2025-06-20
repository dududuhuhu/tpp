package com.tpp.threat_perception_platform.service.impl;


import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.response.AgentResponse;
import com.tpp.threat_perception_platform.service.RabbitService;
import com.tpp.threat_perception_platform.service.VerifierService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitServiceImpl implements RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private VerifierService verifierService;

    /**
     * 创建队列
     * @param exchange:交换机的名字
     * @param queue:队列的名字
     * @param routingKey:路由键
     */
    @Override
    public void createAgent(String exchange, String queue, String routingKey) {
        // 创建并绑定队列
        rabbitTemplate.execute(channel -> {
            // 创建队列
            channel.queueDeclare(queue, true, false, false, null);
            // 绑定交换机
            channel.queueBind(queue, exchange, routingKey);
            return null;
        });
    }

    /**
     * 发送消息到rabbitMQ
     * @param exchange
     * @param routingKey
     * @param message
     */
    @Override
    public void sendMessage(String exchange, String routingKey, String message) {
        AgentResponse response = new AgentResponse(message, verifierService.signMessage(message));
        rabbitTemplate.convertAndSend(exchange,routingKey, JSON.toJSONString(response));
    }


}
