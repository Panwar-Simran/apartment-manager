package com.Simran.apartmentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreditResponse {
    private Long userId;
    private String memberName;
    private String flatNumber;
    private BigDecimal creditBalance;
    private LocalDateTime updatedAt;
}
