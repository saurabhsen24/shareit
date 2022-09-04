package com.shareit.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String otp;
    private String email;
    private String newPassword;

}
