package com.blog.carta.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsResponse extends BaseGetListObjectResponse{
    private List<PostResponse> posts;
}
