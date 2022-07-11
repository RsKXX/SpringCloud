package com.rabbitmq.config;


import com.rabbitmq.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExchangeConfig {


  /**
   * 交换器
   * @return
   */
  @Bean
  public DirectExchange directExchange(){
    return new DirectExchange(RabbitMqConstant.EXCHANGE);
  }

  /**
   * 死信队列交换器
   * @return
   */
  @Bean
  public DirectExchange deadExchange(){
    return new DirectExchange(RabbitMqConstant.DEAD_EXCHANGE);
  }

  /**
   * 延时队列交换器
   * @return
   */
  @Bean
  public CustomExchange delayedExchange()
  {
    Map<String,Object> args = new HashMap<>(1);
    args.put("x-delayed-type", "direct");
    return new CustomExchange(RabbitMqConstant.DELAYED_EXCHANGE,"x-delayed-message",true,false,args);
  }

  //队列1
  @Bean
  public Queue queueOne(){
    return QueueBuilder.durable(RabbitMqConstant.QUEUE_ONE).build();
  }
  //队列2
  @Bean
  public Queue queueTwo(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("x-dead-letter-exchange",RabbitMqConstant.DEAD_EXCHANGE);
    map.put("x-dead-letter-routing-key",RabbitMqConstant.DEAD_ROUTE_KEY);
    return QueueBuilder.durable(RabbitMqConstant.QUEUE_TWO).withArguments(map).build();
  }
  //死信队列
  @Bean
  public Queue deadQueue(){
    return new Queue(RabbitMqConstant.DEAD_QUEUE);
  }
  //延时队列
  @Bean
  public Queue delayedQueue(){
    return new Queue(RabbitMqConstant.DELAYED_QUEUE,true);
  }

  @Bean
  public Binding bindingDelayedQueue(){
    return BindingBuilder.bind(delayedQueue()).to(delayedExchange()).with(RabbitMqConstant.DELAYED_ROUTE_KEY).noargs();
  }

  @Bean
  public Binding bindingQueueOne(){
    return BindingBuilder.bind(queueOne()).to(directExchange()).with(RabbitMqConstant.ROUTE_KEY_ONE);
  }

  @Bean
  public Binding bindingQueueTwo(){
    return BindingBuilder.bind(queueTwo()).to(directExchange()).with(RabbitMqConstant.ROUTE_KEY_TWO);
  }

  @Bean
  public Binding bindingDeadQueue(){
    return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(RabbitMqConstant.DEAD_ROUTE_KEY);
  }


}
