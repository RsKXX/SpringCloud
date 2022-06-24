package com.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitDeadConsumer {

    @RabbitListener(queues = "company_dead")
    public void company_dead(Message message, Channel channel) {
        try{
            channel.basicQos(1);
            String s = new String(message.getBody());
            log.info("处理死信"+s);
            //在此处记录到数据库、报警之类的操作
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("接收异常："+e.getMessage());
        }
    }

}
