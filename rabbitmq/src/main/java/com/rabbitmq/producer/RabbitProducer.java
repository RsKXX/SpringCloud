package com.rabbitmq.producer;


import com.rabbitmq.constant.RabbitMqConstant;
import com.rabbitmq.data.entity.TestTime;
import com.rabbitmq.service.TestTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RabbitProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TestTimeService testTimeService;

    public void sendMessageToOne(String str){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_ONE,RabbitMqConstant.ROUTE_KEY_ONE,message,correlationData);
    }


    public void sendMessageToTwo(String str){
        TestTime time = new TestTime().setInformation(str).setStartTime(new Date());
        testTimeService.save(time);
        MessageProperties messageProperties = new MessageProperties();
        str = str+","+time.getId();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("startTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        message.getMessageProperties().setExpiration("10000");
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_ONE,RabbitMqConstant.ROUTE_KEY_TWO,message,correlationData);
    }

}
