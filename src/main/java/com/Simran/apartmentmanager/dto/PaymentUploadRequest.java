package com.Simran.apartmentmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUploadRequest {
    @NotNull(message = "Cycle id is required")
    private Long cycleId;

    @NotBlank(message = "Payment mode is required")
    private String paymentMode;

    private String transactionRef;

    // Screenshot URL from Cloudinary
    private String screenshotUrl;
}
