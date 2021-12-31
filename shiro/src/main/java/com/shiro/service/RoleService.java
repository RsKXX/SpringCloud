package com.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.data.entity.RoleInformation;

import java.util.List;

public interface RoleService extends IService<RoleInformation> {
    List<String> getRolesByUserName(String username);
}
