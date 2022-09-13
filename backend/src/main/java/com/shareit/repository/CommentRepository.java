package com.shareit.repository;

import com.shareit.dto.projection.CommentProjection;
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

    @Query(value = "SELECT c.comment_id as commentId, c.text as text, u.user_name as userName\n" +
            "FROM comment c \n" +
            "INNER JOIN posts p \n" +
            "on c.post_id = p.post_id \n" +
            "INNER JOIN user u\n" +
            "ON c.user_id = u.user_id\n" +
            "WHERE c.post_id=:postId", nativeQuery = true)
    List<CommentProjection> getAllCommentsOnPost( @Param("postId") Long postId );

    @Query( value = "SELECT  c.comment_id as commentId, c.text as text, u.user_name as userName\n" +
            "FROM comment c \n" +
            "INNER JOIN user u \n" +
            "ON c.user_id = u.user_id\n" +
            "WHERE c.comment_id=:commentId", nativeQuery = true )
    Optional<CommentProjection> getComment( @Param("commentId") Long commentId );

    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET text=:commentText WHERE user_id=:userId " +
            "AND comment_id=:commentId", nativeQuery = true)
    Integer updateCommentOnPost(@Param("commentText") String commentText, @Param("commentId") Long commentId,
                                @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment where comment_id=:commentId AND user_id=:userId", nativeQuery = true)
    Integer deleteCommentOnPost(@Param("commentId") Long commentId, @Param("userId") Long userId);

}
