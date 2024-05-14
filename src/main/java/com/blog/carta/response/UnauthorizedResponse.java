package com.blog.carta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedResponse {
    private int status;
    private String message;
}
