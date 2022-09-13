package com.shareit.service.impl;

import com.shareit.dto.projection.CommentProjection;
import com.shareit.dto.request.CommentRequestDto;
import com.shareit.dto.response.CommentResponseDto;
import com.shareit.entities.Comment;
import com.shareit.entities.Post;
import com.shareit.entities.User;
import com.shareit.exception.ForbiddenResourceException;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.CommentRepository;
import com.shareit.service.CommentService;
import com.shareit.service.PostService;
import com.shareit.service.UserService;
import com.shareit.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Override
    public List<CommentResponseDto> getAllCommentsOnPost(Long postId) {
        return commentRepository.getAllCommentsOnPost(postId).stream().map(comment -> CommentResponseDto.from(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getComment(Long commentId) {
        CommentProjection comment = commentRepository.getComment(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        return CommentResponseDto.builder().commentId(comment.getCommentId())
                .userName(comment.getUserName())
                .commentText(comment.getText())
                .build();
    }

    @Override
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto) {

        log.info("Saving comment {} in db", commentRequestDto.getText());

        Post post = postService.findPostByPostId(postId);
        User user = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());

        Comment comment = Comment.builder()
                .text(commentRequestDto.getText())
                .post(post)
                .user(user)
                .build();

        log.info("Comment {} saved in DB", commentRequestDto.getText());
        comment = commentRepository.save(comment);

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .commentText(comment.getText())
                .userName(user.getUserName()).build();
    }

    @Override
    public CommentResponseDto updateComment( Long commentId, CommentRequestDto commentRequestDto ) {
        User loggedInUser = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());
        Integer countOfUpdatedRecords = commentRepository.updateCommentOnPost(commentRequestDto.getText(), commentId,
                loggedInUser.getUserId());

        log.info("Updated records: {}", countOfUpdatedRecords);

        if( countOfUpdatedRecords == 0 ) {
            throw new ForbiddenResourceException("You are not authorized to update others comments");
        }

        return CommentResponseDto.builder()
                .commentId(commentId)
                .commentText(commentRequestDto.getText())
                .userName(loggedInUser.getUserName())
                .build();

    }

    @Override
    public void deleteComment(Long commentId) {
        User loggedInUser = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());
        Integer countOfDeletedRecords = commentRepository.deleteCommentOnPost( commentId, loggedInUser.getUserId() );
        log.info("Total Number of Deleted Records {}", countOfDeletedRecords);

        if( countOfDeletedRecords == 0 ) {
            throw new ForbiddenResourceException("You are not authorized to delete others comments");
        }
    }
}
