package com.rabbitmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.data.entity.TestTime;
import com.rabbitmq.mapper.TestTimeMapper;
import com.rabbitmq.service.TestTimeService;
import org.springframework.stereotype.Service;

@Service
public class TestTimeServiceImpl extends ServiceImpl<TestTimeMapper, TestTime> implements TestTimeService {
}
