package com.redisson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redisson.data.entity.User;
import com.redisson.mapper.db2.UserMapper;
import com.redisson.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void savaUser(User user) {
        save(user);
    }

    @Override
    public void delUser(String id) {

        this.baseMapper.deleteById(id);

    }
}
