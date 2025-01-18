package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.config.JWTAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private JWTAuthenticationConfig jWTAuthenticationConfig;

    @Value("${security.default.username}")
    private String defaultUsername;

    @Value("${security.default.password}")
    private String defaultPassword;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (defaultUsername.equals(username) && defaultPassword.equals(password)) {
            String token = jWTAuthenticationConfig.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
