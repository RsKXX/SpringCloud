package com.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transaction.data.entity.User;
import com.transaction.mapper.UserMapper;
import com.transaction.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void savaUser(User user) {
        save(user);
    }
}
