package com.blog.carta.service;

import com.blog.carta.payload.CommentDto;
import com.blog.carta.response.CommentResponse;
import com.blog.carta.response.CommentsResponse;
import com.blog.carta.response.MessageResponse;


public interface CommentService {
    CommentResponse createComment(Long postId, CommentDto commentDto);

    CommentsResponse getAllCommentsByPostId(Long postId, int pageNumber, int pageSize, String sortBy, String sortDirection);

    CommentResponse getCommentByCommentId(Long postId, Long commentId);

    CommentResponse updateCommentByCommentId(Long postId, Long commentId, CommentDto commentDto);

    MessageResponse deleteCommentByCommentId(Long postId, Long commentId);
}
