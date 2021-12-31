package com.shiro.data.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("a_role_permission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="角色—权限关系表", description="角色—权限关系表")
public class RolePermission {

    private String id;
    private String roleId;
    private String permissionId;
}
