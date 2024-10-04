package com.services.security.controller;

import com.services.security.model.AuthRequest;
import com.services.security.model.AuthValidationRequest;
import com.services.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private  UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails.getUsername()));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody AuthValidationRequest authValidationRequest) throws Exception {

        return ResponseEntity.ok(jwtUtil.validateToken(authValidationRequest.getToken(), authValidationRequest.getUserName()));
    }
}

