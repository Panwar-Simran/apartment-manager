package com.Simran.apartmentmanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaintenanceCycleRequest {
    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Year is required")
    @Min(value = 2024, message = "Year must be 2024 or later")
    private Integer year;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Amount must be greater than 0")
    private BigDecimal amountPerMember;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}
