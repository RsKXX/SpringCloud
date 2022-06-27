package com.rabbitmq.controller;


import com.rabbitmq.producer.RabbitProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SendMessageController")
@Slf4j
public class SendMessageController {
    @Autowired
    private RabbitProducer rabbitProducer;
    @GetMapping("/test/{str}")
    public void sendMessageToOne(@PathVariable("str") String str){
        rabbitProducer.sendMessageToOne(str);
    }
}
