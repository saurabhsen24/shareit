package com.shareit.service;

import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.PostResponseDto;
import com.shareit.entities.Post;

import java.util.List;

public interface PostService {

    void createPost(PostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById( Long postId );

    void deletePost( Post post );

    void updatePost( Post post );
}
