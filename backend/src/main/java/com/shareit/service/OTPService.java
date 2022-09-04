package com.shareit.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private static final Integer EXPIRE_MIN = 4;
    private String key;
    private LoadingCache<String,Integer> otpCache;

    public OTPService(){
        super();
        otpCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public int generateOTP(String key){
        this.key = key;
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(100000);
        otpCache.put(key,otp);
        return otp;
    }

    public int getOtp(String key){
        try {
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    public boolean validateOtp(int otpNumber){
        return this.getOtp(this.key) == otpNumber;
    }

    public void clearOTP(){
        otpCache.invalidate(this.key);
    }

}
