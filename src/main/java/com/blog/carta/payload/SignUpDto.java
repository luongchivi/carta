package com.blog.carta.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDto {
    private String name;

    @NotEmpty(message = "Username is required")
    @Size(max = 35, message = "Username must be at most 35 characters")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;
}
