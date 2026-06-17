package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ChangePasswordRequest;
import com.Simran.apartmentmanager.dto.LoginRequest;
import com.Simran.apartmentmanager.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    String changePassword(ChangePasswordRequest request);
}
