package com.Simran.apartmentmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String flatNumber;
    private String role;
    private Boolean isActive;
    private Boolean isPasswordChanged;
    private LocalDateTime createdAt;
}
