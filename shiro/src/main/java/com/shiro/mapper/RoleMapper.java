package com.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.data.entity.RoleInformation;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleInformation> {
    List<String> getRolesByUserName(String username);
}
