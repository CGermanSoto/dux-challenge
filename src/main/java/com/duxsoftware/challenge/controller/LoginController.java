package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.config.JWTAuthenticationConfig;
import com.duxsoftware.challenge.dto.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Login")
    @Operation(summary = "Authentication Token", description = "Permite a los usuarios autenticarse y obtener un token JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (defaultUsername.equals(username) && defaultPassword.equals(password)) {
            String token = jWTAuthenticationConfig.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username o password invalido.");
    }
}
