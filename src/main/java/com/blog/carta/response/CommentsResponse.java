package com.blog.carta.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentsResponse extends BaseGetListObjectResponse {
    private List<CommentResponse> comments;
}
