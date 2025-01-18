package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.config.JWTAuthtenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    private static final String DEFAULT_USERNAME = "test";
    private static final String DEFAULT_PASSWORD = "12345";

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        logger.info("Received login request with username: " + username);

        // Validación de usuario
        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            String token = jwtAuthtenticationConfig.getJWTToken(username);
            logger.info("User authenticated successfully, returning JWT token.");
            return ResponseEntity.ok(Map.of("token", token));
        }
        logger.warning("Invalid username or password for username: " + username);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
