package com.redisson.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redisson.data.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInformationMapper extends BaseMapper<UserInformation> {
}
