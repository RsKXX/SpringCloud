package com.rabbitmq.producer;


import com.rabbitmq.constant.RabbitMqConstant;
import com.rabbitmq.data.entity.TestTime;
import com.rabbitmq.service.TestTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class RabbitProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TestTimeService testTimeService;

    /**
     * 发送到direct交换器，正常路由到队列消费
     * @param str
     */
    public void sendMessageToOne(String str){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE,RabbitMqConstant.ROUTE_KEY_ONE,message,correlationData);
    }

    /**
     * 发送到队列二 设置过期时间，消息流转到死信队列，变成延时队列
     * @param str
     */
    public void sendMessageToTwo(String str){
        TestTime time = new TestTime().setInformation(str).setStartTime(new Date());
        testTimeService.save(time);
        MessageProperties messageProperties = new MessageProperties();
        str = str+","+time.getId();
        messageProperties.setContentType("application/json");
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("startTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //设置消息 过期时间，消息发送到队列2，当过期时间到了  消息没有被消费，就会转到 添加的死信的队列，可以做出简易的延时队列
        message.getMessageProperties().setExpiration("10000");
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE,RabbitMqConstant.ROUTE_KEY_TWO,message,correlationData);
    }

    /**
     * 发送到延时队列
     * @param str
     * @param delayed
     */
    public void sendMessageToDelayedQueue(String str,Integer delayed){
        TestTime time = new TestTime().setInformation(str).setStartTime(new Date());
        testTimeService.save(time);
        MessageProperties messageProperties = new MessageProperties();
        str = str+","+time.getId();
        messageProperties.setContentType("application/json");
        messageProperties.setDelay(delayed);
        Message message = new Message(str.getBytes(StandardCharsets.UTF_8),messageProperties);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("startTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        rabbitTemplate.convertAndSend(RabbitMqConstant.DELAYED_EXCHANGE,RabbitMqConstant.DELAYED_ROUTE_KEY,message,correlationData);
    }

}
