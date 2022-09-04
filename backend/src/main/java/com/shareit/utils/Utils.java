package com.shareit.utils;

import com.google.gson.Gson;

import java.security.SecureRandom;


public class Utils {

    public static Gson gson;

    static {
        gson = new Gson();
    }


    public static String getOTP() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(100000);
        return String.valueOf(otp);
    }
}
