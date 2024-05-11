package com.blog.carta.service.impl;

import com.blog.carta.entity.Comment;
import com.blog.carta.entity.Post;
import com.blog.carta.exception.ResourceNotFoundException;
import com.blog.carta.payload.PostDto;
import com.blog.carta.repository.CommentRepository;
import com.blog.carta.repository.PostRepository;
import com.blog.carta.response.CommentResponse;
import com.blog.carta.response.MessageResponse;
import com.blog.carta.response.PostResponse;
import com.blog.carta.response.PostsResponse;
import com.blog.carta.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.blog.carta.utils.Constants.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public PostResponse createPost(PostDto postDto) {

        // convert postDto into entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        // convert entity into Response
        PostResponse postResponse = mapToResponse(newPost);
        return postResponse;
    }

    @Override
    public PostsResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equals(DEFAULT_SORT_DIRECTION) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<PostResponse> postsData = posts.stream().map(post -> mapToResponse(post)).collect(Collectors.toList());

        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(postsData);
        postsResponse.setPageNumber(posts.getNumber() + 1);
        postsResponse.setPageSize(posts.getSize());
        postsResponse.setTotalElements(posts.getTotalElements());
        postsResponse.setTotalPages(posts.getTotalPages());
        postsResponse.setLastPage(posts.isLast());

        return postsResponse;
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        PostResponse postResponse = mapToResponse(post);
        return postResponse;
    }

    @Override
    public PostResponse updatePostById(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // save to database
        postRepository.save(post);

        PostResponse postResponse = mapToResponse(post);
        return postResponse;
    }

    @Override
    public MessageResponse deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        List<Comment> comments = commentRepository.findByPostId(id); // tìm comments có post_id = id và delete nó

        commentRepository.deleteAll(comments);
        postRepository.delete(post);

        return new MessageResponse("Delete post and associated comments successfully");
    }

    // convert PostDto into entity post
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    // convert entity post into Response
    private PostResponse mapToResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setContent(post.getContent());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUpdatedAt(post.getUpdatedAt());

        // Loop qua từng comment để convert sang CommentResponse trong PostResponse
        Set<CommentResponse> commentsResponse = post.getComments().stream()
                .map(comment -> {
                    CommentResponse commentResponse = new CommentResponse();
                    commentResponse.setId(comment.getId());
                    commentResponse.setName(comment.getName());
                    commentResponse.setEmail(comment.getEmail());
                    commentResponse.setBody(comment.getBody());
                    commentResponse.setCreatedAt(comment.getCreatedAt());
                    commentResponse.setUpdatedAt(comment.getUpdatedAt());
                    return commentResponse;
                })
                .collect(Collectors.toSet());
        postResponse.setComments(commentsResponse);

        return postResponse;
    }
}
