package com.transaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.transaction.data.entity.User;
import com.transaction.service.UserService;


@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    @Transactional
    public void add() {
        test1();
        test2();
    //        throw new NullPointerException("xxxx112");
        }
//    @Transactional
    public void test1(){
        User user = new User().setUsername("test1").setPassword("test1").setId("1502205858879172609");
        userService.savaUser(user);
    }
//    @Transactional
    public void test2(){
        User user = new User().setUsername("test2").setPassword("test2");
        userService.savaUser(user);
        throw new ClassCastException("test2123");
    }
}
