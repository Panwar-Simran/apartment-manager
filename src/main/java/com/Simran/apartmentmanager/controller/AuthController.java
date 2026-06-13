package com.Simran.apartmentmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("login")
    public ResponseEntity<?>login()
    {
        return null;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword() {
        return null;
    }

}
