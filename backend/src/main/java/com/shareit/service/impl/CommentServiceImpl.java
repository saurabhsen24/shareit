package com.shareit.service.impl;

import com.shareit.entities.Comment;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.repository.CommentRepository;
import com.shareit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllCommentsOnPost(Long postId) {
        return null;
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found!"));
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
