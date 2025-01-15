package com.polling.polling_api.controllers;

import com.polling.polling_api.services.AuthService;
import com.polling.polling_api.dtos.AuthRequestDTO;
import com.polling.polling_api.dtos.AuthResponseDTO;
import com.polling.polling_api.dtos.RegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequestDTO registerRequest){
        authService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authCredentials) {
        AuthResponseDTO authResponse = authService.authenticate(authCredentials);
        return ResponseEntity.ok(authResponse);
    }
}
