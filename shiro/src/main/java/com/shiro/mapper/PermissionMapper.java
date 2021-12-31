package com.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.data.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<String> getPermissionsByUserName(String username);
}
