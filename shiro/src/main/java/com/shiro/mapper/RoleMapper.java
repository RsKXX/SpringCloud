package com.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.data.entity.RoleInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleInformation> {
    List<String> getRolesByUserName(@Param("username") String username);
}
