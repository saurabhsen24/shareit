package com.shareit.repository;

import com.shareit.entities.Vote;
import com.shareit.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT SUM(vote_type) FROM vote where post_id=:postId and vote_type=:voteType",nativeQuery = true)
    Integer getAllVotes(@Param("postId") Long postId, @Param("voteType") VoteType voteType);

    @Query(value = "SELECT * FROM vote where user_id=:userId AND post_id=:postId",nativeQuery = true)
    Optional<Vote> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

}
