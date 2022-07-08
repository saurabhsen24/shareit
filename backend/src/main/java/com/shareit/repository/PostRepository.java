package com.shareit.repository;

import com.shareit.dto.projection.PostProjection;
import com.shareit.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.post_id as postId ," +
            "p.post_title as postTitle," +
            "p.post_desc as postDescription," +
            "p.post_url as postUrl, p.vote_count as voteCount , u.user_name as userName " +
            "FROM posts p INNER JOIN user u ON" +
            " p.user_id = u.user_id",nativeQuery = true)
    List<PostProjection> findAllPosts();

    @Query(value = "SELECT u.user_name FROM posts p INNER JOIN user u ON p.user_id = u.user_id WHERE p.post_id=:postId",
            nativeQuery = true)
    String findPostCreator(@Param("postId") Long postId);
}
