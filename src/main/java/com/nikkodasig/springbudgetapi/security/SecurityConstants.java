package com.nikkodasig.springbudgetapi.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH_KEY = "Authorization";
    public static final long EXPIRATION_TIME = 3600000; // 3600000 ms = 60 mins, 30000 ms = 5 mins
}
