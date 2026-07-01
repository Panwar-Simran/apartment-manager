package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MemberCreditResponse;

import java.math.BigDecimal;

public interface MemberCreditService {

    // Get logged in member's own credit balance
    MemberCreditResponse getMyCredit();

    // Used internally by other services (extra contributions, payments)
    BigDecimal getCreditBalance(Long userId);

    void addCredit(Long userId, BigDecimal amount);

    void deductCredit(Long userId, BigDecimal amount);
}
