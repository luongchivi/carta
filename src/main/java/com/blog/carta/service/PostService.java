package com.blog.carta.service;

import com.blog.carta.payload.PostDto;
import com.blog.carta.response.MessageResponse;
import com.blog.carta.response.PostResponse;
import com.blog.carta.response.PostsResponse;


public interface PostService {
    // tạo phương thức createPost xữ lý bên PostServiceImpl
    // sẽ có nhiều phương thức interface như createPost, deletePost, updatePost, getPost, getAllPosts,....
    PostResponse createPost(PostDto postDto);

    PostsResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostResponse getPostById(Long id);

    PostResponse updatePostById(Long id, PostDto postDto);

    MessageResponse deletePostById(Long id);
}
