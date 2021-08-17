package com.hexdump95.organization.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hexdump95.organization.security.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.secret:defaultSecretKey}")
    private String jwtSecret;

    @Value("${jwt.expiresAtMinutes:20}")
    private Long expiresAt;

    @Value("${jwt.issuer:hexdump95}")
    private String issuer;

    public LoginResponse createLoginResponse(Authentication auth){
        long dateNow = System.currentTimeMillis();
        long expireTime = dateNow + expiresAt * 60 * 1000;

        List<String> roles = auth.getAuthorities()
                .stream().map(a -> a.getAuthority().substring(5))
                .collect(Collectors.toList());

        String jwt = JWT.create()
                .withIssuer(issuer)
                .withSubject(auth.getName())
                .withExpiresAt(new Date(expireTime))
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(jwtSecret));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken("Bearer " + jwt);
        loginResponse.setUsername(auth.getName());
        loginResponse.setExpiresAt(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(expireTime),
                        TimeZone.getDefault().toZoneId()));

        return loginResponse;
    }

    public DecodedJWT decodeJwt(String authHeader) {
        String jwt = authHeader.substring(7);
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .build().verify(jwt);
    }

    public List<GrantedAuthority> getDecodedAuthorities(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("roles").asList(String.class)
                .stream()
                .map(r -> "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
