package com.shareit.controller;

import com.shareit.dto.UserDto;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
