package com.shareit.service.impl;

import com.nimbusds.jose.util.IntegerUtils;
import com.shareit.dto.request.CommentRequestDto;
import com.shareit.dto.response.CommentResponseDto;
import com.shareit.entities.Comment;
import com.shareit.entities.Post;
import com.shareit.entities.User;
import com.shareit.exception.BadRequestException;
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
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found!"));
    }

    @Override
    public void createComment(Long postId, CommentRequestDto commentRequestDto) {

        log.info("Saving comment {} in db", commentRequestDto.getText());

        Post post = postService.findPostByPostId(postId);
        User user = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());

        Comment comment = Comment.builder()
                .text(commentRequestDto.getText())
                .post(post)
                .user(user)
                .build();

        log.info("Comment {} saved in DB", commentRequestDto.getText());
        commentRepository.save(comment);
    }

    @Override
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        User loggedInUser = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());
        Integer countOfUpdatedRecords = commentRepository.updateCommentOnPost(commentRequestDto.getText(), postId, commentId, loggedInUser.getUserId());

        log.info("Updated records: {}", countOfUpdatedRecords);

        if( countOfUpdatedRecords == 0 ) {
            throw new ForbiddenResourceException("You are not authorized to update others comments");
        }

        return CommentResponseDto.builder()
                .commentText(commentRequestDto.getText())
                .userName(loggedInUser.getUserName())
                .build();

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        User loggedInUser = userService.getUserByUsername(JwtHelper.getCurrentLoggedInUsername());
        Integer countOfDeletedRecords = commentRepository.deleteCommentOnPost(postId, commentId, loggedInUser.getUserId());
        log.info("Total Number of Deleted Records {}", countOfDeletedRecords);

        if( countOfDeletedRecords == 0 ) {
            throw new ForbiddenResourceException("You are not authorized to delete others comments");
        }
    }
}
