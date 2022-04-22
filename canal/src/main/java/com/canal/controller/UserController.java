package com.canal.controller;
import com.canal.data.entity.UserInformation;
import com.canal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags="用户")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "添加", notes = "添加")
    @PostMapping(value = "/add")
    public String add(@RequestBody UserInformation user) {
        boolean save = userService.save(user);
        return "success";
    }
}