package com.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.data.entity.UserInformation;
import com.shiro.data.vo.LoginVO;

public interface UserService extends IService<UserInformation> {
    Boolean checkUserAccountStatus(String username);
    Boolean checkUser(LoginVO loginVO);
    UserInformation getUserInfoByUserName(String username);
}
