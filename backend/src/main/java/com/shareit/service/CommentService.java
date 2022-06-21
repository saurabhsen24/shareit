package com.shareit.service;

import com.shareit.entities.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsOnPost(String postId);

    Comment getComment(String commentId);

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteComment(Comment comment);

}
