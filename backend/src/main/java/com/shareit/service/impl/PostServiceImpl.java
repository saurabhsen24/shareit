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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();

        User user = userService.getUserByUsername(userName);

        Post post = Post.builder()
                .postName(postRequestDto.getPostName())
                .postDescription(postRequestDto.getPostDescription())
                .postUrl(postRequestDto.getPostUrl())
                .user(user)
                .build();

        postRepository.save(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> PostResponseDto.from(post)).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));
        return PostResponseDto.from(post);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }
}
