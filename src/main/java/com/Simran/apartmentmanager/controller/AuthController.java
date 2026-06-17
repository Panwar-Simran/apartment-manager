package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.ChangePasswordRequest;
import com.Simran.apartmentmanager.dto.LoginRequest;
import com.Simran.apartmentmanager.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@Valid @RequestBody LoginRequest request)
    {
        return null;
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return null;
    }

}
