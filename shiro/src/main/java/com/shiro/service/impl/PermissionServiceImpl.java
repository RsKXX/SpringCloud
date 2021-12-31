package com.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.shiro.data.entity.Permission;
import com.shiro.mapper.PermissionMapper;
import com.shiro.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<String> getPermissionsByUserName(String username) {
        return this.baseMapper.getPermissionsByUserName(username);
    }
}
