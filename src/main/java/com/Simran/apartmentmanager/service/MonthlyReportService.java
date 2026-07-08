package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.MonthlyReportResponse;

public interface MonthlyReportService {
    // Generate monthly report for given month and year
    // Shows total collected, total expenses, closing balance
    // Member wise payment status and expense details
    MonthlyReportResponse getMonthlyReport(int month, int year);
}
