package com.mybatis.mapper.db2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
