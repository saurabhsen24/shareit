package com.shareit.controller;

import com.shareit.dto.request.*;
import com.shareit.dto.response.AuthResponse;
import com.shareit.dto.response.GenericResponse;
import com.shareit.service.AuthService;
import com.shareit.service.EmailService;
import com.shareit.service.OTPService;
import com.shareit.utils.JwtHelper;
import com.shareit.utils.Utils;
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

    @Autowired
    private OTPService otpService;

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

    @ApiOperation(value = "Generate OTP", response = GenericResponse.class)
    @PostMapping(value = "/generateOTP")
    public ResponseEntity<GenericResponse> generateOTP(@RequestBody ForgetPasswordRequest forgetPasswordRequest)
            throws MessagingException {
        otpService.generateOTP(Utils.getOTP(), forgetPasswordRequest.getEmail());
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("OTP is sent to the recipient, please check your email"));
    }

    @ApiOperation(value = "Validates OTP", response = GenericResponse.class)
    @PutMapping(value = "/validateOTP")
    public ResponseEntity<GenericResponse> validateOTP(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        //otpService.validateOTP(resetPasswordRequest.getOtp(), resetPasswordRequest.getEmail());
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Password updated successfully"));
    }

    @ApiOperation(value = "Updates Password", response = GenericResponse.class)
    @PutMapping(value = "/changePassword")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.updatePassword(changePasswordRequest);
        return ResponseEntity.ok(GenericResponse.buildGenericResponse("Password updated successfully"));
    }

}
