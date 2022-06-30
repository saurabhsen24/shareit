package com.shareit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtHelper {

    @Autowired
    private RSAPrivateKey privateKey;

    @Value("${jwtExpirationTime}")
    private Integer jwtExpirationAmount;

    @Autowired
    private RSAPublicKey publicKey;

    public String createJwtForClaims(String subject, Map<String,String> claims){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.MONTH, jwtExpirationAmount);

        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
        claims.forEach(jwtBuilder::withClaim);

        return jwtBuilder
                .withIssuer("share it")
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey,privateKey));
    }

    public String getTokenExpirationTime() {
        return jwtExpirationAmount + Constants.MONTH;
    }
}
