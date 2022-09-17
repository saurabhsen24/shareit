package com.shareit.service;

import com.shareit.dto.request.CommentRequestDto;
import com.shareit.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> getAllCommentsOnPost(Long postId);

    CommentResponseDto getComment(Long commentId);

    CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto);

    CommentResponseDto updateComment( Long commentId, CommentRequestDto commentRequestDto );

    void deleteComment( Long commentId );

}
