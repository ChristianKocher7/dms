package org.bfh.dms.security.jwt;

public class JwtSecurityConstants {

    static final String SECRET = "dOjfFeW484H5V4qALpoXncyM3";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String AUTH_URL = "/login";
    static final long EXPIRATION_TIME = 900000L; //15 minutes

}
