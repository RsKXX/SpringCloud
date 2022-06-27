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

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class RabbitMqConfig {

  @Autowired
  private RabbitTemplate rabbitTemplate;

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



  @PostConstruct
  public void configureRabbitTemplate() {
    // 比如在这里设置接收消息后的回调方法
    rabbitTemplate.setConfirmCallback(new ConfirmCallbackImpl());
    rabbitTemplate.setReturnsCallback(new ReturnsCallbackImpl());
  }


  /**
   * 成功后的回调方法
   */
  public static class ConfirmCallbackImpl implements ConfirmCallback {

    /**
     * 实现confirm方法
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("success：data："+correlationData);
        log.info("success：ack："+ack);
        log.info("success：cause："+cause);
    }
  }


  /**
   * 失败后的回调方法
   */
  public static class ReturnsCallbackImpl implements ReturnsCallback {


    @Override
    public void returnedMessage(ReturnedMessage returned) {
      log.info("return：data："+returned);
    }
  }
}
