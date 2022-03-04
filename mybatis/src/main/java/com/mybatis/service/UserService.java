package com.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.data.entity.User;

public interface UserService extends IService<User> {
    void savaUser(User user);
    void delUser(String id);
}

