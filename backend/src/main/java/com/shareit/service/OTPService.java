package com.shareit.service;

import javax.mail.MessagingException;

public interface OTPService {
    void generateOTP( String otp , String email ) throws MessagingException;

    void validateOTP( String otp, String email );

}
