package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ExpenseResponse;
import com.Simran.apartmentmanager.dto.MonthlyReportResponse;
import com.Simran.apartmentmanager.dto.PaymentResponse;
import com.Simran.apartmentmanager.entity.Expense;
import com.Simran.apartmentmanager.entity.MaintenanceCycle;
import com.Simran.apartmentmanager.entity.PaymentRecord;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.ExpenseRepository;
import com.Simran.apartmentmanager.repository.MaintenanceCycleRepository;
import com.Simran.apartmentmanager.repository.PaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
    @Autowired
    MaintenanceCycleRepository maintenanceCycleRepository;

    @Autowired
    PaymentRecordRepository paymentRecordRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Override
   public MonthlyReportResponse getMonthlyReport(int month, int year){
       // Step 1 - Find maintenance cycle for this month and year
       MaintenanceCycle cycle = maintenanceCycleRepository
               .findByMonthAndYear(month, year)
               .orElseThrow(() -> new ResourceNotFoundException("No maintenance cycle found for month: "
                                       + month + " year: " + year));

       // Step 2 - Get all payment records for this cycle
       List<PaymentRecord> paymentRecords =
               paymentRecordRepository.findByCycleId(cycle.getId());


       // Step 3 - Calculate collection summary
       BigDecimal totalExpected = BigDecimal.ZERO;
       BigDecimal totalCollected = BigDecimal.ZERO;
       BigDecimal totalPending = BigDecimal.ZERO;

       List<PaymentResponse> paymentDetails = new ArrayList<>();

       for (PaymentRecord record : paymentRecords) {

           // Add to total expected
           totalExpected = totalExpected.add(
                   cycle.getAmountPerMember());

           // Add to collected or pending
           if (record.getStatus().equals("PAID")) {
               totalCollected = totalCollected.add(
                       record.getPaidAmount());
           } else {
               totalPending = totalPending.add(
                       record.getFinalDue());
           }

           // Build payment response
           PaymentResponse paymentResponse = new PaymentResponse();
           paymentResponse.setId(record.getId());
           paymentResponse.setCycleId(cycle.getId());
           paymentResponse.setMonth(cycle.getMonth());
           paymentResponse.setYear(cycle.getYear());
           paymentResponse.setUserId(record.getUser().getId());
           paymentResponse.setMemberName(record.getUser().getName());
           paymentResponse.setFlatNumber(
                   record.getUser().getFlatNumber());
           paymentResponse.setPaidAmount(record.getPaidAmount());
           paymentResponse.setCreditUsed(record.getCreditUsed());
           paymentResponse.setFinalDue(record.getFinalDue());
           paymentResponse.setPaymentMode(record.getPaymentMode());
           paymentResponse.setTransactionRef(
                   record.getTransactionRef());
           paymentResponse.setScreenshotUrl(
                   record.getScreenshotUrl());
           paymentResponse.setStatus(record.getStatus());
           paymentResponse.setPaymentDate(record.getPaymentDate());
           paymentResponse.setCreatedAt(record.getCreatedAt());

           paymentDetails.add(paymentResponse);
       }//for

       // Step 4 - Get all expenses for this month and year
       List<Expense> expenses = expenseRepository
               .findByMonthAndYear(month, year);

       BigDecimal totalExpenses = BigDecimal.ZERO;
       List<ExpenseResponse> expenseDetails = new ArrayList<>();

       for (Expense expense : expenses) {

           // Add to total expenses
           totalExpenses = totalExpenses.add(expense.getAmount());

           // Build expense response
           ExpenseResponse expenseResponse = new ExpenseResponse();
           expenseResponse.setId(expense.getId());
           expenseResponse.setCategoryId(
                   expense.getCategory().getId());
           expenseResponse.setCategoryName(
                   expense.getCategory().getName());
           expenseResponse.setCategoryType(
                   expense.getCategory().getType());
           expenseResponse.setAddedByName(
                   expense.getAddedBy().getName());
           expenseResponse.setAmount(expense.getAmount());
           expenseResponse.setDescription(expense.getDescription());
           expenseResponse.setExpenseDate(expense.getExpenseDate());
           expenseResponse.setReceiptUrl(expense.getReceiptUrl());
           expenseResponse.setCreatedAt(expense.getCreatedAt());

           expenseDetails.add(expenseResponse);
       }

       // Step 5 - Calculate closing balance
       // closing balance = total collected - total expenses
       BigDecimal closingBalance = totalCollected.subtract(
               totalExpenses);

       // Step 6 - Build final report response
       MonthlyReportResponse report = new MonthlyReportResponse();
       report.setMonth(month);
       report.setYear(year);
       report.setTotalExpected(totalExpected);
       report.setTotalCollected(totalCollected);
       report.setTotalPending(totalPending);
       report.setTotalExpenses(totalExpenses);
       report.setClosingBalance(closingBalance);
       report.setPaymentDetails(paymentDetails);
       report.setExpenseDetails(expenseDetails);

       return report;

   }

}
