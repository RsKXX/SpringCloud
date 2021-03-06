package com.rabbitmq.producer;


import com.rabbitmq.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RabbitProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageToOne(String str){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_ONE,RabbitMqConstant.ROUTE_KEY_ONE,message,correlationData);
    }


    public void sendMessageToTwo(String str){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_ONE,RabbitMqConstant.ROUTE_KEY_TWO,message,correlationData);
    }

}
