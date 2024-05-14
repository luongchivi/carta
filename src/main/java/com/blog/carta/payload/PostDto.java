package com.blog.carta.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data // tạo class payloadPostDto cho createPost
public class PostDto {
    private Long id;

    @NotEmpty(message = "Title  is required")
    private String title;

    @NotEmpty(message = "Description  is required")
    private String description;

    @NotEmpty(message = "Content  is required")
    private String content;
}
