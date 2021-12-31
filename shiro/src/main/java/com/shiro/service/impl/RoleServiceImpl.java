package com.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.data.entity.RoleInformation;
import com.shiro.mapper.RoleMapper;
import com.shiro.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleInformation> implements RoleService {

    @Override
    public List<String> getRolesByUserName(String username) {
        return this.baseMapper.getRolesByUserName(username);
    }
}
