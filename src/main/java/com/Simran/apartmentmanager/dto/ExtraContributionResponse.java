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
public class ExtraContributionResponse {
    private Long id;
    private Long userId;
    private String memberName;
    private String flatNumber;
    private String description;
    private BigDecimal amount;
    private LocalDate contributionDate;
    private String proofUrl;
    private String status;
    private String approvedByName;
    private LocalDateTime createdAt;
}
