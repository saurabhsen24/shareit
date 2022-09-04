package com.shareit.controller;

import com.shareit.dto.request.ForgetPasswordRequest;
import com.shareit.dto.request.LoginRequest;
import com.shareit.dto.request.ResetPasswordRequest;
import com.shareit.dto.request.SignupRequest;
import com.shareit.dto.response.AuthResponse;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.AuthService;
import com.shareit.utils.JwtHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtHelper jwtHelper;


    @ApiOperation(value = "Login user", response = AuthResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "Login Successful"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }


    @ApiOperation(value = "Signup User", response = GenericResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Signup Successful"),
            @ApiResponse(code = 401, message = "You are not authenticated")
    })
    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signUpUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authService.signupUser(signupRequest),HttpStatus.OK);
    }

    @ApiOperation(value = "Forget Password", response = GenericResponse.class)
    @PostMapping(value = "/forgetPassword")
    public ResponseEntity<GenericResponse> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest)
            throws MessagingException {
        authService.forgetPassword(forgetPasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("OTP is sent to the recipient, please check your email"));
    }

    @ApiOperation(value = "Reset Password", response = GenericResponse.class)
    @PutMapping(value = "/resetPassword")
    public ResponseEntity<GenericResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Password updated successfully"));
    }

}
