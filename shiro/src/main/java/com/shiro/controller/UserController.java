package com.shiro.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shiro.config.RedisService;
import com.shiro.constant.CommonConstant;
import com.shiro.data.entity.*;
import com.shiro.data.vo.LoginVO;
import com.shiro.data.vo.Result;
import com.shiro.service.*;
import com.shiro.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags="基础")
@RestController
@RequestMapping("/basic")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RedisService redisService;

    //------------------查看，添加角色和权限--------start-----------
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping(value = "/addRole")
    public void addRole(@RequestBody RoleInformation role) {
        roleService.save(role);
    }

    @ApiOperation(value = "用户赋予角色", notes = "用户赋予角色")
    @PostMapping(value = "/addUserRole")
    public void addUserRole(@RequestBody UserRole userRole) {
        userRoleService.save(userRole);
    }

    @ApiOperation(value = "添加权限", notes = "添加权限")
    @PostMapping(value = "/addPermission")
    public void addPermission(@RequestBody Permission permission) {
        permissionService.save(permission);
    }

    @ApiOperation(value = "给角色添加权限", notes = "给角色添加权限")
    @PostMapping(value = "/addRolePermission")
    public void addRolePermission(@RequestBody RolePermission rolePermission) {
        rolePermissionService.save(rolePermission);
    }

    @ApiOperation(value = "查询用户角色", notes = "查询用户角色")
    @GetMapping(value = "/getUserRole/{username}")
    public List<RoleInformation> getUserRole(@PathVariable String username) {
        //查询用户id
        UserInformation user = userService.getOne(new LambdaQueryWrapper<UserInformation>().eq(UserInformation::getUsername, username));
        //查询用户的角色id 列表
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //查询用户角色列表
        return roleService.listByIds(roleIds);
    }

    @ApiOperation(value = "查询用户权限", notes = "查询用户权限")
    @GetMapping(value = "/getUserPermission/{username}")
    public List<Permission> getUserPermission(@PathVariable String username) {
        //查询用户id
        UserInformation user = userService.getOne(new LambdaQueryWrapper<UserInformation>().eq(UserInformation::getUsername, username));
        //查询用户的角色id 列表
        List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //查询用户角色对应的权限id 列表
        List<RolePermission> rolePermissions = rolePermissionService.list(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds));
        List<String> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        //查询用户权限列表
        return permissionService.listByIds(permissionIds);
    }
    //------------------添加,查看角色和权限------end-------------

    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping(value = "/register")
    public Result register(@RequestBody UserInformation user) {
        boolean save = userService.save(user);
        Result<Object> result = new Result<>();
        result.setResult(save);
        return result;
    }


    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginVO loginVO) {
        Result<Object> result = new Result<>();
        //校验账户状态是否正常
        String username = loginVO.getUsername();
        Boolean status = userService.checkUserAccountStatus(username);
        if (!status) {
            result.setMessage("用户状态不正常");
            result.setCode(500);
            return result;
        }
        //校验用户名,密码是否匹配
        Boolean flag = userService.checkUser(loginVO);
        if (!flag) {
            result.setMessage("用户名或密码错误");
            result.setCode(500);
            return result;
        }
        //校验部门
        /**
         * TODO
         */
        //生成token
        String token = JwtUtil.sign(username, loginVO.getPassword());
        // 设置token缓存有效时间  30分钟
        redisService.set(CommonConstant.PREFIX_USER_TOKEN + token, token,30*60*1000);
        loginVO.setToken(token);
        result.setResult(loginVO);
        result.setMessage("success");
        result.setCode(200);
        return result;
    }


}