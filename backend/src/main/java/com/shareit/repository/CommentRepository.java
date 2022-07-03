package com.shareit.repository;

import com.shareit.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment c INNER JOIN posts p on c.post_id = p.post_id " +
            "WHERE c.post_id=:postId", nativeQuery = true)
    List<Comment> getAllCommentsOnPost( @Param("postId") Long postId );

    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET text=:commentText WHERE post_id=:postId AND user_id=:userId " +
            "AND comment_id=:commentId", nativeQuery = true)
    Integer updateCommentOnPost(@Param("commentText") String commentText, @Param("postId") Long postId ,
                                          @Param("commentId") Long commentId, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment where post_id=:postId AND comment_id=:commentId AND user_id=:userId", nativeQuery = true)
    Integer deleteCommentOnPost(@Param("postId") Long postId, @Param("commentId") Long commentId,
                                          @Param("userId") Long userId);

}
