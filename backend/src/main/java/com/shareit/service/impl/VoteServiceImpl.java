package com.shareit.service.impl;

import com.shareit.entities.Vote;
import com.shareit.enums.VoteType;
import com.shareit.repository.VoteRepository;
import com.shareit.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Vote updateVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void delete(Vote vote) {
        voteRepository.delete(vote);
    }

    @Override
    public Integer getAllVotesOnPost(Long postId, VoteType voteType) {
        return voteRepository.getAllVotes(postId, voteType);
    }
}
