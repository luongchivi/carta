package com.blog.carta.service;

import com.blog.carta.payload.CommentDto;
import com.blog.carta.response.CommentResponse;
import com.blog.carta.response.CommentsResponse;


public interface CommentService {
    CommentResponse createComment(Long postId, CommentDto commentDto);

    CommentsResponse getAllCommentsByPostId(Long postId, int pageNumber, int pageSize, String sortBy, String sortDirection);
}
