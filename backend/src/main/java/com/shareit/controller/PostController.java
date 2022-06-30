package com.shareit.controller;

import com.shareit.dto.PostDto;
import com.shareit.dto.request.PostRequestDto;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Created new post for the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post created!"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/create")
    public ResponseEntity<GenericResponse> addPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        log.debug("Received post creation request {}", postRequestDto.getPostName());
        postService.createPost(postRequestDto);
        return new ResponseEntity<>(new GenericResponse("Post successfully created"), HttpStatus.OK);
    }
}
