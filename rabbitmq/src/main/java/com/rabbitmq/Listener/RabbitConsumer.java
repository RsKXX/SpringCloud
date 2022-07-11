package com.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.constant.RabbitMqConstant;
import com.rabbitmq.data.entity.TestTime;
import com.rabbitmq.service.TestTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Slf4j
public class RabbitConsumer {

    @Autowired
    private TestTimeService testTimeService;

    /**
     * 正常队列
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConstant.QUEUE_ONE)
    public void listenerQueueOne(Message message, Channel channel) throws IOException {
        try{
        channel.basicQos(1);
        String str = new String(message.getBody());
        log.info(str);
        //抛出错误。
//        int i = 1/0;
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e) {
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            if (redelivered) {
                log.error("异常重试次数已到达设置次数，将发送到死信交换机");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息即将返回队列处理重试");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }


    /**
     * 死信队列
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConstant.DEAD_QUEUE)
    public void deadLetterTwo(Message message, Channel channel) {
        //延时队列 演示消费信息
        try{
            channel.basicQos(1);
            String s = new String(message.getBody());
            String[] strings = s.split(",");
            testTimeService.updateById(new TestTime().setId(strings[1]).setEndTime(new Date()));
            log.info("endTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            log.info("处理死信"+s);
            //在此处记录到数据库、报警之类的操作
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("接收异常："+e.getMessage());
        }
    }


    /**
     * 延时队列
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConstant.DELAYED_QUEUE)
    public void delayedQueue(Message message, Channel channel) {
        //延时队列 演示消费信息
        try{
            channel.basicQos(1);
            String s = new String(message.getBody());
            String[] strings = s.split(",");
            testTimeService.updateById(new TestTime().setId(strings[1]).setEndTime(new Date()));
            log.info("endTime:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            log.info("处理延时消息:"+s);
            //在此处记录到数据库、报警之类的操作
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("接收异常："+e.getMessage());
        }
    }
}
