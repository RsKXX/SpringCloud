package com.shiro.controller;

import com.shiro.data.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="测试权限")
@RestController
@RequestMapping("/test/shiro")
@Slf4j
public class TestController {

    @ApiOperation(value = "获取资源", notes = "获取资源")
    @GetMapping(value = "/getResource")
    public Result getResource() {
        Result<Object> result = new Result<>();
        result.setMessage("获取资源");
        return result;
    }
}
