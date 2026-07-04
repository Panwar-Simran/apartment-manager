package com.Simran.apartmentmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseCategoryResponse {
    private Long id;
    private String name;
    private String type;
    private String frequency;
    private LocalDateTime createdAt;
}
