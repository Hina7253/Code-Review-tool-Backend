package com.codereview.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
}
