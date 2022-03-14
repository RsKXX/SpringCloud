package com.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transaction.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
