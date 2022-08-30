package com.shareit.service.impl;

import com.shareit.dto.request.EmailRequest;
import com.shareit.entities.OTP;
import com.shareit.exception.ResourceNotFoundException;
import com.shareit.exception.TimeExpiredException;
import com.shareit.repository.OTPRepository;
import com.shareit.service.EmailService;
import com.shareit.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OTPServiceImpl implements OTPService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPRepository otpRepository;

    @Override
    public void generateOTP( String otp, String email ) throws MessagingException {

        log.debug("OTP {} saved in db for user {}", otp, email);

        OTP otpEntity = OTP.builder()
                .otp(otp)
                .email(email)
                .build();

        Optional<OTP> otpOptional = otpRepository.findByEmail(email);

        if(otpOptional.isPresent()) {
            otpRepository.delete(otpOptional.get());
        }

        otpRepository.save(otpEntity);

        String message = "<h3> Your OTP: " + otp + " </h3>";

        EmailRequest emailRequest = EmailRequest.builder().to(email)
                .message(message).subject("OTP Verification").build();

        emailService.sendEmail(emailRequest);
    }

    @Override
    public void validateOTP( String otp, String email ) {

        log.debug("Validation of OTP {} started for email {}", otp, email);

        OTP otpEntity = otpRepository.findByEmail(email).orElse(null);

        if(Objects.isNull(otpEntity)) {
            throw new ResourceNotFoundException("OTP not found");
        }

        long currentTime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
        long otpSentTime = TimeUnit.MILLISECONDS.toMinutes(otpEntity.getOtpSentOn().getTime());

        if( currentTime - otpSentTime >= 2){
            throw new TimeExpiredException("OTP expired, Try again with new OTP");
        }

    }
}
