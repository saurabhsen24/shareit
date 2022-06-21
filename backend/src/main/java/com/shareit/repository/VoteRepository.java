package com.shareit.repository;

import com.shareit.entities.Vote;
import com.shareit.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    @Query(value = "SELECT SUM(vote_type) FROM vote where post_id=:postId and vote_type=:voteType",nativeQuery = true)
    Integer getAllVotes(@Param("postId") String postId, @Param("voteType") VoteType voteType);
}
