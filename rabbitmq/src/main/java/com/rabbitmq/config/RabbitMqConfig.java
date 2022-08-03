package com.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {
  @Autowired
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setConfirmCallback(new ConfirmCallbackImpl());
    rabbitTemplate.setReturnsCallback(new ReturnsCallbackImpl());
    return rabbitTemplate;
  }

  /**
   * 自定义JSON消息序列化器, 默认就是json
   */
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    return factory;
  }

  /**
   * 成功后的回调方法
   */
  public static class ConfirmCallbackImpl implements ConfirmCallback {
    /**
     * 实现confirm方法
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息：data："+correlationData);
        log.info("是否成功：ack："+ack);
        log.info("原因：cause："+cause);
    }
  }


  /**
   * 消息从交换器路由到队列失败后的回调方法
   */
  public static class ReturnsCallbackImpl implements ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
      log.info("消息发送失败，退回交换机：data："+returned);
    }
  }
}
