package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.ChangePasswordRequest;
import com.Simran.apartmentmanager.dto.LoginRequest;
import com.Simran.apartmentmanager.dto.LoginResponse;
import com.Simran.apartmentmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@Valid @RequestBody LoginRequest request)
    {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        String message = authService.changePassword(request);

        return ResponseEntity.ok(Map.of("message", message));
    }

}
