package com.shareit.service.impl;

import com.shareit.dto.request.VoteRequestDto;
import com.shareit.dto.response.VoteResponseDto;
import com.shareit.entities.Post;
import com.shareit.entities.User;
import com.shareit.entities.Vote;
import com.shareit.exception.BadRequestException;
import com.shareit.repository.VoteRepository;
import com.shareit.service.PostService;
import com.shareit.service.UserService;
import com.shareit.service.VoteService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;

    @Override
    public VoteResponseDto createVote(Long postId, Long userId, VoteRequestDto voteRequestDto) {
        Post post = postService.findPostByPostId(postId);
        User user = userService.getUserById(userId);

        Optional<Vote> voteOptional = voteRepository.findByUserIdAndPostId(userId, postId);

        if( voteOptional.isPresent() ) {
            throw new BadRequestException("User already voted on a post");
        }

        Vote vote = Vote.builder()
                .post(post)
                .user(user)
                .voteType(voteRequestDto.getVoteType())
                .build();

        voteRepository.save(vote);
        Integer updatedVoteCount = postService.updateVoteCount(post, voteRequestDto.getVoteType());
        return VoteResponseDto.buildVoteResponseDto(updatedVoteCount, voteRequestDto.getVoteType());
    }

    @Override
    public VoteResponseDto updateVote(Long postId, Long userId, VoteRequestDto voteRequestDto) {

        Post post = postService.findPostByPostId(postId);
        Vote vote = voteRepository.findByUserIdAndPostId(userId, postId).orElseThrow(() ->
                new BadRequestException("Post or User is Invalid"));

        Integer updatedVoteCount = post.getVoteCount();

        if(BooleanUtils.isFalse(vote.getVoteType().equals(voteRequestDto.getVoteType()))) {
            updatedVoteCount = postService.updateVoteCount(post, voteRequestDto.getVoteType());
            vote.setVoteType(voteRequestDto.getVoteType());
            voteRepository.save(vote);
        }

        return VoteResponseDto.buildVoteResponseDto(updatedVoteCount, voteRequestDto.getVoteType());
    }

}
