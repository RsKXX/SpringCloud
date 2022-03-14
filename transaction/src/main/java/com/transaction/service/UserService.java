package com.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.transaction.data.entity.User;

public interface UserService extends IService<User> {
    void savaUser(User user);
}

