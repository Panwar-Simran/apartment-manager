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
public class PaymentResponse {
    private Long id;
    private Long cycleId;
    private Integer month;
    private Integer year;
    private Long userId;
    private String memberName;
    private String flatNumber;
    private BigDecimal paidAmount;
    private BigDecimal creditUsed;
    private BigDecimal finalDue;
    private String paymentMode;
    private String transactionRef;
    private String screenshotUrl;
    private String status;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;
}
