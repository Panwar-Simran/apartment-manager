package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ChangePasswordRequest;
import com.Simran.apartmentmanager.dto.LoginRequest;
import com.Simran.apartmentmanager.dto.LoginResponse;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.UserRepository;
import com.Simran.apartmentmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Step 1 - Verify email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Step 2 - Load user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Step 3 - Generate JWT token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        // Step 4 - Return response
        return new LoginResponse(
                token,
                user.getRole(),
                user.getIsPasswordChanged()
        );
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {

        // Step 1 - Get logged in user email from SecurityContext
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        // Step 2 - Load user from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Step 3 - Verify current password
        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        // Step 4 - Check new and confirm password match
        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // Step 5 - Encode and save new password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));
        user.setIsPasswordChanged(true);
        user.setTempPassword(null);

        // Step 6 - Save to DB
        userRepository.save(user);

        return "Password changed successfully";
    }
}