package com.shareit.controller;

import com.shareit.dto.request.CommentRequestDto;
import com.shareit.dto.response.CommentResponseDto;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Created Comment", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment created successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/{postId}/createComment")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("postId") Long postId,
                                                         @RequestBody @Valid CommentRequestDto commentRequestDto) {
        log.debug("Received request to create comment on post {}", postId);
        return new ResponseEntity<>(commentService.createComment(postId, commentRequestDto), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Fetches all comments from post", response = CommentResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetches all comments from post"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping("/{postId}/comments")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOnPost(@PathVariable("postId") Long postId){
        log.debug("Received request to get all comments from post {}", postId);
        return new ResponseEntity<>(commentService.getAllCommentsOnPost(postId), HttpStatus.OK);
    }

    @ApiOperation(value = "Fetches single comment from the post", response = CommentResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetches comment from the post"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("commentId") Long commentId){
        log.debug("Received request to fetch comment {}", commentId);
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @ApiOperation(value = "Updates comment on post", response = CommentResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updates comment on post"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You are not authorized")
    })
    @PatchMapping("/editComment/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentResponseDto> updatesComment( @PathVariable("commentId") Long commentId,
                                            @RequestBody @Valid CommentRequestDto commentRequestDto){
        log.debug("Received request to update comment");
        return new ResponseEntity<>(commentService.updateComment( commentId, commentRequestDto),HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes comment on post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted Successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You are not authorized")
    })
    @DeleteMapping("/deleteComment/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> deleteComment( @PathVariable("commentId") Long commentId ) {
        commentService.deleteComment( commentId );
        return new ResponseEntity<>(GenericResponse.buildGenericResponse("Comment Deleted Successfully"),HttpStatus.OK);
    }

}
