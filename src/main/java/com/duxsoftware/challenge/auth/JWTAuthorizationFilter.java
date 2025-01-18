package com.duxsoftware.challenge.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    private static final String TOKEN_BEARER_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9"; // Clave más segura

    private static final Logger logger = Logger.getLogger(JWTAuthorizationFilter.class.getName());

    private Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaimsFromToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HEADER_AUTHORIZATION_KEY);
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            logger.info("No Authorization header found.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(TOKEN_BEARER_PREFIX, "");
        try {
            logger.info("Parsing JWT token.");
            Claims claims = getClaimsFromToken(token);
            String username = claims.getSubject();
            if (username != null) {
                logger.info("Token successfully parsed, username: " + username);
                List<String> roles = claims.get("authorities", List.class);
                var authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Authentication set for username: " + username);
            }
        } catch (Exception e) {
            logger.severe("JWT token parsing failed: " + e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
