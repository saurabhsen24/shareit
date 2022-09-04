package com.shareit.service;

import com.shareit.dto.request.CommentRequestDto;
import com.shareit.dto.response.CommentResponseDto;
import com.shareit.entities.Comment;

import java.util.List;

public interface CommentService {

    List<CommentResponseDto> getAllCommentsOnPost(Long postId);

    Comment getComment(Long commentId);

    void createComment(Long postId, CommentRequestDto commentRequestDto);

    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto);

    void deleteComment(Long postId, Long commentId);

}
