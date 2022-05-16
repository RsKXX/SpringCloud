package com.redisson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redisson.data.entity.UserInformation;
import com.redisson.mapper.db1.UserInformationMapper;
import com.redisson.service.UserInformationService;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl extends ServiceImpl<UserInformationMapper, UserInformation> implements UserInformationService {

    @Override
    public void savaUser(UserInformation user) {
        save(user);
    }

    @Override
    public void delUser(String id) {
        this.baseMapper.deleteById(id);
        int x = 1/0;
    }
}
