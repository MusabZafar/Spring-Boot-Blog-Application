package com.springboot.blogApp.Service;

import DTO_payLoad.PostDto;
import DTO_payLoad.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
//    List<PostDto> getAllPosts();
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);
    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto,Long id);

    List<PostDto> getPostsByCategory(Long categoryId);

    void deletePostById(Long id);
}
