package com.example.app.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate customRabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Long imageId) {
        customRabbitTemplate.convertAndSend(exchange, routingkey, imageId);
        System.out.println("Send msg to rabbit image id = " + imageId );
    }
}
