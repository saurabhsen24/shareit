package com.shareit.service;

import com.shareit.entities.Vote;
import com.shareit.enums.VoteType;

public interface VoteService {

    Vote createVote(Vote vote);

    Vote updateVote(Vote vote);

    void delete(Vote vote);

    Integer getAllVotesOnPost(Long postId,VoteType voteType);
}
