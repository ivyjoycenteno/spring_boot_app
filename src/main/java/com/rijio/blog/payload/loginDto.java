package com.rijio.blog.payload;

import lombok.Data;

@Data
public class loginDto {
    private String usernameOrEmail;
    private String password;
}
