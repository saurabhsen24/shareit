package com.shareit.controller;

import com.shareit.dto.UserDto;
import com.shareit.dto.request.ChangePasswordRequest;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Deletes User from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You are not authorized")
    })
    @DeleteMapping("/removeUser/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(GenericResponse.buildGenericResponse("User Deleted Successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully updated"),
            @ApiResponse(code = 401, message = "You are not authenticated"),
            @ApiResponse(code = 403, message = "You are not authorized")
    })
    @PatchMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates Password", response = GenericResponse.class)
    @PutMapping(value = "/changePassword")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.updatePassword(changePasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Password updated successfully"));
    }

    @ApiOperation(value = "Uploads Profile Pic of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Uploaded Profile URL"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse> uploadUserProfilePic(@RequestPart(value = "file") MultipartFile file) throws IOException {
        String userPicUrl = userService.uploadProfilePic( file );
        return ResponseEntity.ok( GenericResponse.buildGenericResponse( userPicUrl ) );
    }

    @ApiOperation(value = "Gets User Details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched user details successfully"),
            @ApiResponse(code = 401, message = "You are not authorized")
    })
    @GetMapping("/{userName}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("userName") String userName) {
        return ResponseEntity.ok(userService.getUserDetails(userName));
    }

}
