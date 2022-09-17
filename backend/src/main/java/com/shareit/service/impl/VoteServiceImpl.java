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
import com.shareit.utils.JwtHelper;
import com.shareit.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;

    @Override
    public VoteResponseDto voteOnPost(Long postId, VoteRequestDto voteRequestDto) {
        Post post = postService.findPostByPostId(postId);
        String userName = JwtHelper.getCurrentLoggedInUsername();
        User user = userService.getUserByUsername(userName);

        Optional<Vote> voteOptional = voteRepository.findByUserIdAndPostId(user.getUserId(), postId);

        if( voteOptional.isPresent() ) {
            return updateVote(post, voteOptional.get(), voteRequestDto);
        }

        return createVote( post, user, voteRequestDto );
    }

    private VoteResponseDto createVote(Post post, User user, VoteRequestDto voteRequestDto) {

        Vote vote = Vote.builder()
                .post(post)
                .user(user)
                .voteType(voteRequestDto.getVoteType())
                .build();

        voteRepository.save(vote);
        Integer updatedVoteCount = postService.updateVoteCount(post, voteRequestDto.getVoteType());
        return VoteResponseDto.buildVoteResponseDto(updatedVoteCount, voteRequestDto.getVoteType());
    }


    private VoteResponseDto updateVote(Post post, Vote vote, VoteRequestDto voteRequestDto) {

        if( vote.getVoteType() == voteRequestDto.getVoteType() ){
            throw new BadRequestException("User already " + Utils.getVoteTypeMap().get(voteRequestDto.getVoteType())
                    + " this Post");
        }

        Integer updatedVoteCount = post.getVoteCount();

        if(BooleanUtils.isFalse(vote.getVoteType() == voteRequestDto.getVoteType() )) {
            updatedVoteCount = postService.updateVoteCount(post, voteRequestDto.getVoteType());
            vote.setVoteType(voteRequestDto.getVoteType());
            voteRepository.save(vote);
        }

        return VoteResponseDto.buildVoteResponseDto(updatedVoteCount, voteRequestDto.getVoteType());
    }

}
