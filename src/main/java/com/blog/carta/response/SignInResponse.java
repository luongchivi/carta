package com.blog.carta.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
