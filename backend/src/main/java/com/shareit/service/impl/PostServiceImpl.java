package com.shareit.service.impl;

import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.PostResponseDto;
import com.shareit.entities.Post;
import com.shareit.entities.User;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.PostRepository;
import com.shareit.service.PostService;
import com.shareit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createPost(PostRequestDto postRequestDto) {

        log.debug("Creating post {}", postRequestDto.getPostTitle());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();

        User user = userService.getUserByUsername(userName);

        Post post = Post.builder()
                .postTitle(postRequestDto.getPostTitle())
                .postDescription(postRequestDto.getPostDescription())
                .postUrl(postRequestDto.getPostUrl())
                .user(user)
                .build();

        log.debug("Post {} saved successfully", post.getPostTitle());
        postRepository.save(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        log.debug("Getting all the posts from db");
        return postRepository.findAll().stream().map(post -> PostResponseDto.from(post)).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = getPost(postId);
        return PostResponseDto.from(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        log.info("Updating post {}", postId);

        Post updatedPost = getPost(postId);

        if (StringUtils.isNotBlank(postRequestDto.getPostTitle())) {
            updatedPost.setPostTitle(postRequestDto.getPostTitle());
        }

        if (StringUtils.isNotBlank(postRequestDto.getPostDescription())) {
            updatedPost.setPostDescription(postRequestDto.getPostDescription());
        }

        if (StringUtils.isNotBlank(postRequestDto.getPostUrl())) {
            updatedPost.setPostUrl(postRequestDto.getPostUrl());
        }

        log.info("Post updated successfully {}", postId);
        postRepository.save(updatedPost);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));
    }
}
