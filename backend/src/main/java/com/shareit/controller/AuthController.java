package com.shareit.controller;

import com.shareit.dto.response.AuthResponse;
import com.shareit.dto.response.GenericResponse;
import com.shareit.dto.request.LoginRequest;
import com.shareit.dto.request.SignupRequest;
import com.shareit.service.AuthService;
import com.shareit.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signUpUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<GenericResponse>(authService.signupUser(signupRequest),HttpStatus.OK);
    }
}
