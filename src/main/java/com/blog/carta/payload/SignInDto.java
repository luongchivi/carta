package com.blog.carta.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInDto {
    @NotEmpty(message = "Username or Email is required")
    private String usernameOrEmail;

    @NotEmpty(message = "Password  is required")
    private String password;
}
