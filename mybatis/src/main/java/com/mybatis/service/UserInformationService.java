package com.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.data.entity.User;
import com.mybatis.data.entity.UserInformation;

public interface UserInformationService extends IService<UserInformation> {
    void savaUser(UserInformation user);
    void delUser(String id);
}

