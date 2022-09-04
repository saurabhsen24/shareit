package com.shareit.security;

import com.shareit.dto.ErrorMessage;
import com.shareit.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2AuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        logger.error(ex.getLocalizedMessage(), ex);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Unauthorized")
                .description(ex.getMessage())
                .timestamp(new Date())
                .build();

        String errorJson = Utils.gson.toJson(errorMessage);
        response.getWriter().write(errorJson);
    }
}