package com.rabbitmq.Listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.data.entity.TestTime;
import com.rabbitmq.service.TestTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class RabbitDeadConsumer {

    @Autowired
    private TestTimeService testTimeService;

    @RabbitListener(queues = "dead.queue.one")
    public void deadLetterOne(Message message, Channel channel) {
        try{
            channel.basicQos(1);
            String s = new String(message.getBody());
            log.info("处理死信1"+s);
            //在此处记录到数据库、报警之类的操作
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            log.error("接收异常："+e.getMessage());
        }
    }


    @RabbitListener(queues = "dead.queue.two")
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

}
