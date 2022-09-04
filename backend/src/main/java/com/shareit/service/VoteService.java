package com.shareit.service;

import com.shareit.dto.request.VoteRequestDto;
import com.shareit.dto.response.VoteResponseDto;

public interface VoteService {

    VoteResponseDto createVote(Long postId, Long userId, VoteRequestDto voteRequestDto);

    VoteResponseDto updateVote(Long postId, Long userId, VoteRequestDto voteRequestDto);

}
