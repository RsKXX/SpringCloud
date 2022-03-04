package com.mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis.config.MultiDataSourceTransactional;
import com.mybatis.data.entity.User;
import com.mybatis.data.entity.UserInformation;
import com.mybatis.service.UserInformationService;
import com.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @MultiDataSourceTransactional
    public void addUserInfo(){
        User user = new User().setUsername("a").setPassword("x");
        UserInformation userInformation = new UserInformation().setUsername("b").setPassword("x");
        userService.savaUser(user);
        userInformationService.savaUser(userInformation);
    }


    @GetMapping("/del")
    @MultiDataSourceTransactional
    public void delUserInfo(){
        List<User> users = userService.getBaseMapper().selectList(new QueryWrapper<User>());
        List<UserInformation> userInfos = userInformationService.getBaseMapper().selectList(new QueryWrapper<UserInformation>());
        //模拟删除
        userService.delUser(users.get(0).getId());
        userInformationService.delUser(userInfos.get(0).getId());
    }
}
