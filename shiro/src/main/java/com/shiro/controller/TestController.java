package com.shiro.controller;

import com.shiro.data.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="测试")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @ApiOperation(value = "获取资源-user角色", notes = "获取资源-admin权限")
    @GetMapping(value = "/getUserRecourse")
    @RequiresRoles("user")
    public Result getUserRecourse() {
        Subject subject = SecurityUtils.getSubject();
        Result<Object> result = new Result<>();
        result.setMessage("获取user资源");
        return result;
    }

    @ApiOperation(value = "获取资源-admin角色", notes = "获取资源-admin权限")
    @GetMapping(value = "/getAdminRecourse")
    @RequiresRoles("admin")
    public Result getAdminRecourse() {
        Result<Object> result = new Result<>();
        result.setMessage("获取admin资源");
        return result;
    }

    @ApiOperation(value = "添加-add权限", notes = "添加")
    @GetMapping(value = "/add")
    @RequiresPermissions("add")
    public Result add(){
        Result<Object> result = new Result<>();
        result.setMessage("添加成功");
        return result;
    }

    @ApiOperation(value = "删除-delete权限", notes = "删除")
    @GetMapping(value = "/delete")
    @RequiresPermissions("delete")
    public Result delete(){
        Result<Object> result = new Result<>();
        result.setMessage("删除成功");
        return result;
    }
}
