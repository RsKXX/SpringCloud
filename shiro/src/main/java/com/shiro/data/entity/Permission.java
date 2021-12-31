package com.shiro.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("a_permission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="权限表", description="权限表")
public class Permission {
    private String id;
    private String permission;
    private String name;
}
