package com.blog.carta.service.impl;

import com.blog.carta.entity.Comment;
import com.blog.carta.entity.Post;
import com.blog.carta.exception.ResourceNotFoundException;
import com.blog.carta.payload.CommentDto;
import com.blog.carta.repository.CommentRepository;
import com.blog.carta.repository.PostRepository;
import com.blog.carta.response.CommentResponse;
import com.blog.carta.response.CommentsResponse;
import com.blog.carta.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.blog.carta.utils.Constants.DEFAULT_SORT_DIRECTION;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentResponse createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        CommentResponse commentResponse = mapToResponse(newComment);

        return commentResponse;
    }

    @Override
    public CommentsResponse getAllCommentsByPostId(Long postId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equals(DEFAULT_SORT_DIRECTION) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        List<CommentResponse> commentsData = comments.stream().map(
                comment -> mapToResponse(comment)
        ).collect(Collectors.toList());

        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.setComments(commentsData);
        commentsResponse.setPageNumber(comments.getNumber() + 1);
        commentsResponse.setPageSize(comments.getSize());
        commentsResponse.setTotalElements(comments.getTotalElements());
        commentsResponse.setTotalPages(comments.getTotalPages());
        commentsResponse.setLastPage(comments.isLast());
        return commentsResponse;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return comment;
    }

    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setEmail(comment.getEmail());
        commentResponse.setName(comment.getName());
        commentResponse.setBody(comment.getBody());
        commentResponse.setId(comment.getId());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getCreatedAt());
        commentResponse.setPostId(comment.getPost().getId());

        return commentResponse;
    }
}
