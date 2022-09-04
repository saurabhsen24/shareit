package com.shareit.service.impl;

import com.shareit.dto.request.*;
import com.shareit.dto.response.AuthResponse;
import com.shareit.dto.response.GenericResponse;
import com.shareit.entities.User;
import com.shareit.exception.BadRequestException;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.service.*;
import com.shareit.utils.Constants;
import com.shareit.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

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

        claims.put(Constants.AUTHORITIES_CLAIM_NAME, authorities);

        String jwt = jwtHelper.createJwtForClaims(Constants.CLAIMS_USERNAME, claims);

        return AuthResponse.builder()
                .userName(loginRequest.getUsername())
                .accessToken(jwt)
                .refreshToken(refreshTokenService.getRefreshToken(loginRequest.getUsername()))
                .expiresAt(jwtHelper.getTokenExpirationTime())
                .role(authorities)
                .build();
    }

    @Override
    public GenericResponse signupUser(SignupRequest signupRequest) {

        log.info("Signup request initiated for user {}", signupRequest.getUsername());

        if(userService.checkIfUserExistsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())) {
            throw new BadRequestException("Username/Email already exists");
        }

        User user = User.builder()
                .userName(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .build();

        userService.saveUser(user);

        log.info("Signup successful for user {}", signupRequest.getUsername());

        return GenericResponse.builder()
                .message("User successfully signed up!")
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

        log.debug("Updating the user's {} password {}", resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword());

        if( BooleanUtils.isFalse( otpService.validateOtp( Integer.parseInt( resetPasswordRequest.getOtp() ) ) ) ){
            throw new BadRequestException("Invalid OTP or OTP expired, Please try with new OTP");
        }

        User user = userService.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userService.saveUser(user);

    }

    @Override
    public void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        log.debug("Forget password for user {}", forgetPasswordRequest.getEmail());

        userService.findByEmail(forgetPasswordRequest.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User not found with this email"));

        int otp = otpService.generateOTP(forgetPasswordRequest.getEmail().trim());
        String message = "<h3> Your OTP: " + otp + " </h3>";

        EmailRequest emailRequest = EmailRequest.builder().to(forgetPasswordRequest.getEmail().trim())
                .message(message).subject("OTP Verification").build();

        emailService.sendEmail(emailRequest);
    }
}
