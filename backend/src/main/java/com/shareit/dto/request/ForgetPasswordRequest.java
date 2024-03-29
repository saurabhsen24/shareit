package com.shareit.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ForgetPasswordRequest {

    @Email
    private String email;

}
