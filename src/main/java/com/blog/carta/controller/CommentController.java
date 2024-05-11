package com.blog.carta.controller;

import com.blog.carta.payload.CommentDto;
import com.blog.carta.response.CommentResponse;
import com.blog.carta.service.CommentService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blog.carta.response.CommentsResponse;


import static com.blog.carta.utils.Constants.*;
import static com.blog.carta.utils.Constants.DEFAULT_SORT_DIRECTION;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST /api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    // GET /api/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentsResponse> getAllCommentsByPostId(
            @PathVariable(name = "postId") long postId,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(1) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_COMMENTS_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return new ResponseEntity<>(commentService.getAllCommentsByPostId(postId, pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }
}
