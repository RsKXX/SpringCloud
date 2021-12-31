package com.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.data.entity.Permission;

import java.util.List;


public interface PermissionService extends IService<Permission> {
    List<String> getPermissionsByUserName(String username);
}
