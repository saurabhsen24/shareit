package com.shareit.service;

import com.shareit.dto.request.VoteRequestDto;
import com.shareit.dto.response.VoteResponseDto;

public interface VoteService {
    VoteResponseDto voteOnPost(Long postId, VoteRequestDto voteRequestDto);

}
