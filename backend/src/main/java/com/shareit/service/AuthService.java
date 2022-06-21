package com.shareit.service;

import com.shareit.dto.request.LoginRequest;
import com.shareit.dto.response.GenericResponse;
import com.shareit.dto.request.SignupRequest;
import com.shareit.dto.response.AuthResponse;

public interface AuthService {
     AuthResponse loginUser(LoginRequest loginRequest);

     GenericResponse signupUser(SignupRequest signupRequest);
}
