package com.blog.carta.controller;

import com.blog.carta.payload.PostDto;
import com.blog.carta.response.MessageResponse;
import com.blog.carta.response.PostResponse;
import com.blog.carta.response.PostsResponse;
import com.blog.carta.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;

import static com.blog.carta.utils.Constants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // GET /api/posts
    @GetMapping
    public ResponseEntity<PostsResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(1) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    // GET /api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    // PUT /api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PostResponse updatePostById(@Valid @PathVariable(name = "id") long id, @RequestBody PostDto postDto) {
        return postService.updatePostById(id, postDto);
    }

    // DELETE /api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePostById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
    }
}
