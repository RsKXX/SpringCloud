package com.shiro.data.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginVO {
    private String username;
    private String password;
    private String token;
}
