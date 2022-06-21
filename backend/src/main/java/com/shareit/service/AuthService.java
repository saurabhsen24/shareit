package com.shareit.service;

import com.shareit.dto.LoginRequest;
import com.shareit.dto.GenericResponse;
import com.shareit.dto.SignupRequest;
import com.shareit.dto.AuthResponse;

public interface AuthService {
     AuthResponse loginUser(LoginRequest loginRequest);

     GenericResponse signupUser(SignupRequest signupRequest);
}
