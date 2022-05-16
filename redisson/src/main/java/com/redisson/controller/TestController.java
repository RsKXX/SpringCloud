package com.redisson.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redisson.data.entity.User;
import com.redisson.data.entity.UserInformation;
import com.redisson.service.UserInformationService;
import com.redisson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private UserService userService;

    @GetMapping("/addInfo")
    public void addUserInfo(){
        User user = new User().setUsername("a").setPassword("x");
        UserInformation userInformation = new UserInformation().setUsername("b").setPassword("x");
        userService.savaUser(user);
        userInformationService.savaUser(userInformation);
    }


    @GetMapping("/del")
    public void delUserInfo(){
        List<User> users = userService.getBaseMapper().selectList(new QueryWrapper<User>());
        List<UserInformation> userInfos = userInformationService.getBaseMapper().selectList(new QueryWrapper<UserInformation>());
        //模拟删除
        userService.delUser(users.get(0).getId());
        userInformationService.delUser(userInfos.get(0).getId());
    }
}
