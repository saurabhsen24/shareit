package com.shareit.service;

import com.shareit.entities.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPostById( String postId );

    void deletePost( Post post );

    void updatePost( Post post );
}
