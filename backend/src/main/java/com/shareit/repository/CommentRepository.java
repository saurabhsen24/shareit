package com.shareit.repository;

import com.shareit.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment c INNER JOIN post p on c.post_id = p.post_id " +
            "WHERE c.post_id=:postId", nativeQuery = true)
    List<Comment> getAllCommentsOnPost( @Param("postId") Long postId );
}
