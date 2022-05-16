package com.redisson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redisson.data.entity.UserInformation;

public interface UserInformationService extends IService<UserInformation> {
    void savaUser(UserInformation user);
    void delUser(String id);
}

