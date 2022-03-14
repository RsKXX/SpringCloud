package com.transaction.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String password;
    private String salt;
}
