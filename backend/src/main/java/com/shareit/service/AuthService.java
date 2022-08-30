package com.shareit.service;

import com.shareit.dto.request.LoginRequest;
import com.shareit.dto.request.ResetPasswordRequest;
import com.shareit.dto.request.SignupRequest;
import com.shareit.dto.response.AuthResponse;
import com.shareit.dto.response.GenericResponse;

public interface AuthService {
     AuthResponse loginUser(LoginRequest loginRequest);

     GenericResponse signupUser(SignupRequest signupRequest);

     void resetPassword(ResetPasswordRequest resetPasswordRequest);

}
