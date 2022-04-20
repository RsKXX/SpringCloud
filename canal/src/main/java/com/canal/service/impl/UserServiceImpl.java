package com.canal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.canal.data.entity.UserInformation;
import com.canal.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.canal.service.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInformation> implements UserService{
}
