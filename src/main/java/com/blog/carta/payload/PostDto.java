package com.blog.carta.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data // tạo class payloadPostDto cho createPost
public class PostDto {
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String content;
}
