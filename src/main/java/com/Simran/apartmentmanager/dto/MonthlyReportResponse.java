package com.Simran.apartmentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportResponse {
    private int month;
    private int year;

    // Collection summary
    private BigDecimal totalExpected;
    private BigDecimal totalCollected;
    private BigDecimal totalPending;

    // Expense summary
    private BigDecimal totalExpenses;

    // Balance
    private BigDecimal closingBalance;

    // Member wise payment status
    private List<PaymentResponse> paymentDetails;

    // Expense details
    private List<ExpenseResponse> expenseDetails;
}
