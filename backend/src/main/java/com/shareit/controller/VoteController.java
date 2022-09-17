package com.shareit.controller;

import com.shareit.dto.request.VoteRequestDto;
import com.shareit.dto.response.VoteResponseDto;
import com.shareit.service.VoteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @ApiOperation(value = "Vote Post", response = VoteResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vote is created"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<VoteResponseDto> voteOnPost(@PathVariable("postId") Long postId,
                                                      @RequestBody @Valid VoteRequestDto voteRequestDto){
        return new ResponseEntity<>(voteService.voteOnPost(postId, voteRequestDto), HttpStatus.OK);
    }


}
