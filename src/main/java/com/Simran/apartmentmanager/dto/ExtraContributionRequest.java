package com.Simran.apartmentmanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExtraContributionRequest {

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message="Amount is required")
    @DecimalMin(value="0.0",inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Contribution date is required")
    private LocalDate contributionDate;

    private String proofUrl;

}
