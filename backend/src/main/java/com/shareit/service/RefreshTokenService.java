package com.shareit.service;

import com.shareit.entities.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    String getRefreshToken(String username);
}
