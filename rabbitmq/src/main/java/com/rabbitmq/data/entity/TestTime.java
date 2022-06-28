package com.rabbitmq.data.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("test_time")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TestTime {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String information;
    private Date startTime;
    private Date endTime;
}
