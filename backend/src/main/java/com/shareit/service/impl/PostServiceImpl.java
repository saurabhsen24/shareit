package com.shareit.service.impl;

import com.shareit.entities.Post;
import com.shareit.exception.NotFoundException;
import com.shareit.repository.PostRepository;
import com.shareit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(UUID.fromString(postId)).orElseThrow(() -> new NotFoundException("Post not found!"));
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
