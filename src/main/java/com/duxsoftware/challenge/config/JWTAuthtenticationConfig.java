package com.duxsoftware.challenge.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class JWTAuthtenticationConfig {
    private static final String SECRET_KEY = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";
    private static final long TOKEN_EXPIRATION_TIME = 86400000;

    private static final Logger logger = Logger.getLogger(JWTAuthtenticationConfig.class.getName());

    public String getJWTToken(String username) {
        List<String> authorities = AuthorityUtils.createAuthorityList("ROLE_USER")
                .stream().map(authority -> authority.getAuthority())
                .toList();

        logger.info("Generating JWT token for username: " + username);

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }
}
