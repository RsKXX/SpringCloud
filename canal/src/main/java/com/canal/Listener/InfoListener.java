package com.canal.Listener;

import com.canal.constant.RabbitMqConstant;
import com.canal.data.entity.CanalMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InfoListener {
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstant.QUEUE_INFO)
    public void data(CanalMessage message){
        System.out.println(message);
    }
}
