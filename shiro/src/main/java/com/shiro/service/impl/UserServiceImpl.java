package com.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.shiro.data.entity.UserInformation;
import com.shiro.data.vo.LoginVO;
import com.shiro.mapper.UserMapper;
import com.shiro.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInformation> implements UserService {

    /**
     * @Author:RuanShaoKang
     * @Date:2021/12/21 15:52
     * @Description: 检查账户状态
     */
    public Boolean checkUserAccountStatus(String username){
        LambdaQueryWrapper<UserInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInformation::getUsername,username);
        UserInformation res = getOne(queryWrapper);
        //通过username查询用户信息，获取账户信息判断账户信息是否正常
        //测试默认状态为都正常
        return true;
    }

    /**
     * @Author:RuanShaoKang
     * @Date:2021/12/21 15:54
     * @Description: 检查用户名和密码
     */
    public Boolean checkUser(LoginVO loginVO){
        LambdaQueryWrapper<UserInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInformation::getUsername,loginVO.getUsername());
        UserInformation user = getOne(queryWrapper);
        if(null == user){
            return false;
        }
        //对数据库的密码进行解密，存在盐值的需要加上盐值，匹配密码
        if(!Objects.equals(loginVO.getPassword(), user.getPassword())){
            return false;
        }
        return true;
    }

    @Override
    public UserInformation getUserInfoByUserName(String username) {
        LambdaQueryWrapper<UserInformation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInformation::getUsername,username);
        return getOne(wrapper);
    }

}
