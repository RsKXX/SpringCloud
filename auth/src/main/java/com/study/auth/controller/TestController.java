package com.study.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/test")
public class TestController {

    @GetMapping("/getReq")
    public String getRequest(){
        return "get请求成功";
    }
}
