package com.Simran.apartmentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String categoryType;
    private String addedByName;
    private BigDecimal amount;
    private String description;
    private LocalDate expenseDate;
    private String receiptUrl;
    private LocalDateTime createdAt;
}
