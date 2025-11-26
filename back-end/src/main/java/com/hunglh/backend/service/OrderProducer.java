package com.hunglh.backend.service;

import com.hunglh.backend.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Object order) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDERS_QUEUE, order);
    }
}
