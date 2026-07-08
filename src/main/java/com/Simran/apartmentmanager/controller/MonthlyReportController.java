package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.MonthlyReportResponse;
import com.Simran.apartmentmanager.service.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")

public class MonthlyReportController {
    @Autowired
    private MonthlyReportService monthlyReportService;

    // Both Pradhana and Member can view monthly report
    @GetMapping("/monthly/{month}/year/{year}")
    public ResponseEntity<MonthlyReportResponse> getMonthlyReport(@PathVariable int month, @PathVariable int year) {
        return new ResponseEntity<>
                (monthlyReportService.getMonthlyReport(month, year), HttpStatus.OK);
    }

}
