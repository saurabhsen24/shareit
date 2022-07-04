package com.shareit.service;

import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.PostResponseDto;
import com.shareit.entities.Post;
import com.shareit.enums.VoteType;

import java.util.List;

public interface PostService {

    void createPost(PostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById( Long postId );

    void deletePost( Long postId );

    void updatePost( Long postId , PostRequestDto postRequestDto );

    Post findPostByPostId( Long postId );

    Integer updateVoteCount(Post post, VoteType voteType);
}
