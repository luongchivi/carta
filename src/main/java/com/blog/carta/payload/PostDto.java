package com.blog.carta.payload;

import lombok.Data;

@Data // tạo class payloadPostDto cho createPost
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
}
