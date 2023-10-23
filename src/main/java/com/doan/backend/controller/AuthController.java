package com.doan.backend.controller;


import com.doan.backend.payload.AuthenticationRequest;
import com.doan.backend.payload.AuthenticationResponse;
import com.doan.backend.payload.RegisterRequest;
import com.doan.backend.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }


    @PostMapping("/admin/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authService.loginUser(authenticationRequest));
    }
}
