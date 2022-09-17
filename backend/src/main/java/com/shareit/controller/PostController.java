package com.shareit.controller;

import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.GenericResponse;
import com.shareit.dto.response.PostResponseDto;
import com.shareit.service.PostService;
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
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Created new post for the user", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post created"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/createPost")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> addPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        log.debug("Received post creation request {}", postRequestDto.getPostTitle());
        postService.createPost(postRequestDto);
        return new ResponseEntity<>(new GenericResponse("Post successfully created"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all posts",response = PostResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Displays all posts"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        log.debug("Received request to show all posts");
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @ApiOperation(value = "Gets post", response = PostResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched post successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("postId") Long postId){
        log.debug("Received post get request {}", postId);
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates post", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post updated successfully"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "Forbidden resource")
    })
    @PutMapping("/updatePost/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> updatePost(@PathVariable("postId") Long postId,@RequestBody @Valid PostRequestDto postRequestDto){
        log.debug("Received request to update post {}", postId);
        postService.updatePost(postId, postRequestDto);
        return new ResponseEntity<>(new GenericResponse("Post updated successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleted post", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post Deleted"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You are not authorized")
    })
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> deletePost(@PathVariable("postId") Long postId){
        log.debug("Received request to delete post {}", postId);
        postService.deletePost(postId);
        return new ResponseEntity<>(new GenericResponse("Post Deleted Successfully"), HttpStatus.OK);
    }

}
