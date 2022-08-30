package com.shareit.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ChangePasswordRequest {

    @Email
    private String email;

    private String oldPassword;

    private String newPassword;

}
