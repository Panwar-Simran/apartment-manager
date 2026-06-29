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
public class MaintenanceCycleResponse {
    private Long id;
    private Integer month;
    private Integer year;
    private BigDecimal amountPerMember;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
}
