package com.shiro.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("a_role_information")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="角色表", description="角色表")
public class RoleInformation {
    private String id;
    private String roleCode;
    private String roleName;
}
