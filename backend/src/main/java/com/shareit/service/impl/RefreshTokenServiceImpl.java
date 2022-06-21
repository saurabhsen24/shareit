package com.shareit.service.impl;

import com.shareit.entities.RefreshToken;
import com.shareit.exception.BadRequestException;
import com.shareit.repository.RefreshTokenRepository;
import com.shareit.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public String getRefreshToken(String username) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByUsername(username);
        return refreshTokenOpt.map(RefreshToken::getToken).orElse(null);
    }

}
