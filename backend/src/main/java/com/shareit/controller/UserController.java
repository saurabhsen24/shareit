package com.shareit.controller;

import com.shareit.dto.UserDto;
import com.shareit.dto.response.GenericResponse;
import com.shareit.entities.User;
import com.shareit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/removeUser/{userId}")
    public ResponseEntity<GenericResponse> removeUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(new GenericResponse("User Deleted Successfully"), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }
}
