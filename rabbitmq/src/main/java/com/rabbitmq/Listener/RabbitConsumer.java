package com.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class RabbitConsumer {

    @RabbitListener(queues = RabbitMqConstant.QUEUE_ONE)
    public void listenerQueueOne(Message message, Channel channel) throws IOException {
        channel.basicQos(1);
        String str = new String(message.getBody());
        log.info(str);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

        Boolean redelivered = message.getMessageProperties().getRedelivered();
        if(redelivered){
            log.error("异常重试次数已到达设置次数，将发送到死信交换机");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }else {
            log.error("消息即将返回队列处理重试");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
