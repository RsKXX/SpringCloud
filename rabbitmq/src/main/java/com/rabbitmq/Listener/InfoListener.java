package com.rabbitmq.Listener;

import com.rabbitmq.constant.RabbitMqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//public class InfoListener {
//    @RabbitHandler
//    @RabbitListener(queues = RabbitMqConstant.QUEUE_INFO)
//    public void data(String message){
//        System.out.println(message);
//    }
//}
