package com.canal.config;


import com.canal.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


}
