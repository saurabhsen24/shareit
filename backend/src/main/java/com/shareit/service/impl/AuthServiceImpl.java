package com.shareit.service.impl;

import com.shareit.dto.LoginRequest;
import com.shareit.dto.GenericResponse;
import com.shareit.dto.SignupRequest;
import com.shareit.dto.AuthResponse;
import com.shareit.entities.User;
import com.shareit.service.AuthService;
import com.shareit.service.RefreshTokenService;
import com.shareit.service.UserService;
import com.shareit.utils.Constants;
import com.shareit.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String,String> claims = new HashMap<>();
        claims.put(Constants.CLAIMS_USERNAME, loginRequest.getUsername());

        String authorities = userService.getUserByUsername(loginRequest.getUsername())
                        .getRoles().stream().map(role -> role.getRoleType().toString())
                .collect(Collectors.joining(","));

        claims.put(Constants.CLAIMS_AUTHORITIES, authorities);

        String jwt = jwtHelper.createJwtForClaims(Constants.CLAIMS_USERNAME, claims);

        return AuthResponse.builder()
                .userName(loginRequest.getUsername())
                .accessToken(jwt)
                .refreshToken(refreshTokenService.getRefreshToken(loginRequest.getUsername()))
                .expiresAt(jwtHelper.getTokenExpirationTime())
                .build();
    }

    @Override
    public GenericResponse signupUser(SignupRequest signupRequest) {

        User user = User.builder()
                .userName(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .build();

        userService.saveUser(user);
        return GenericResponse.builder()
                .message("User successfully signed up!")
                .build();
    }

}
