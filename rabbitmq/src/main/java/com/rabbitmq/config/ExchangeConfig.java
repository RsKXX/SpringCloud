package com.rabbitmq.config;


import com.rabbitmq.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ExchangeConfig {

  //---------------------死信队列开始

  /**
   * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
   */
//  @Bean
//  public Exchange directExchange() {
//    return ExchangeBuilder.directExchange(RabbitMqConstant.EXCHANGE_DIRECT).durable(true).build();
//  }
//
//  /**
//   * 死信队列
//   *
//   * @return
//   */
//  @Bean
//  public Queue deadLetterQueue() {
//    return QueueBuilder.durable(RabbitMqConstant.DEAD_LETTER_QUEUE).deadLetterExchange(RabbitMqConstant.EXCHANGE_DIRECT)
//        .deadLetterRoutingKey("deadKey").build();
//  }

  //---------------------死信部分结束
  /**
   * ============================= 数据topic 交换器 ===== start
   **/
  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(RabbitMqConstant.EXCHANGE_TOPIC, true, false, null);
  }

  @Bean
  public Queue infoQueue() {
    return new Queue(RabbitMqConstant.QUEUE_INFO, true, false, false);
  }


  @Bean
  public Binding infoBinding() {
    //链式写法，绑定交换机和队列，并设置匹配键
    return BindingBuilder
        //绑定队列
        .bind(infoQueue())
        //到交换机
        .to(exchange()).with(RabbitMqConstant.ROUTING_KEY);
  }

  /**
   * =========================================================== 数据topic 交换器 ===== end
   **/





  @Bean("directExchange")
  public DirectExchange directExchange(){
    return new DirectExchange(RabbitMqConstant.EXCHANGE_ONE);
  }

  @Bean("deadExchange")
  public DirectExchange deadExchange(){
    return new DirectExchange(RabbitMqConstant.DEAD_EXCHANGE_ONE);
  }


  @Bean("queueOne")
  public Queue queueOne(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("x-dead-letter-exchange",RabbitMqConstant.DEAD_EXCHANGE_ONE);
    map.put("x-dead-letter-routing-key",RabbitMqConstant.DEAD_ROUTE_KEY_ONE);
    return QueueBuilder.durable(RabbitMqConstant.QUEUE_ONE).withArguments(map).build();
  }

  @Bean("queueTwo")
  public Queue queueTwo(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("x-dead-letter-exchange",RabbitMqConstant.DEAD_EXCHANGE_ONE);
    map.put("x-dead-letter-routing-key",RabbitMqConstant.DEAD_ROUTE_KEY_TWO);
    return QueueBuilder.durable(RabbitMqConstant.QUEUE_TWO).withArguments(map).build();
  }

  @Bean("deadQueueOne")
  public Queue deadQueueOne(){
    return new Queue(RabbitMqConstant.DEAD_QUEUE_ONE);
  }

  @Bean("deadQueueOne")
  public Queue deadQueueTwo(){
    return new Queue(RabbitMqConstant.DEAD_QUEUE_TWO);
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
  public Binding bindingDeadQueueOne(){
    return BindingBuilder.bind(deadQueueOne()).to(deadExchange()).with(RabbitMqConstant.DEAD_ROUTE_KEY_ONE);
  }

  @Bean
  public Binding bindingDeadQueueTwo(){
    return BindingBuilder.bind(deadQueueTwo()).to(deadExchange()).with(RabbitMqConstant.DEAD_ROUTE_KEY_TWO);
  }


}
