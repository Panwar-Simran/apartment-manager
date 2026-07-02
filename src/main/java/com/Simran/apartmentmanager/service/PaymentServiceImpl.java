package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.PaymentResponse;
import com.Simran.apartmentmanager.dto.PaymentUploadRequest;
import com.Simran.apartmentmanager.entity.PaymentRecord;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.PaymentRecordRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberCreditService memberCreditService;

    @Override
    @Transactional
    public PaymentResponse uploadPayment(PaymentUploadRequest request) {

        // Step 1 - Get logged in member
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Step 2 - Find payment record for this cycle and user
        PaymentRecord paymentRecord = paymentRecordRepository
                .findByCycleIdAndUserId(request.getCycleId(), user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment record not found"));

        // Step 3 - Check if already paid
        if (paymentRecord.getStatus().equals("PAID")) {
            throw new BadRequestException("Already paid for this cycle!");
        }

        // Step 4 - Check member credit balance
        BigDecimal creditBalance = memberCreditService
                .getCreditBalance(user.getId());
        BigDecimal amountDue = paymentRecord.getFinalDue();

        BigDecimal creditUsed;
        BigDecimal newFinalDue;

        if (creditBalance.compareTo(amountDue) >= 0) {
            // Credit fully covers the due amount
            creditUsed = amountDue;
            newFinalDue = BigDecimal.ZERO;
        } else {
            // Credit partially covers or zero credit
            creditUsed = creditBalance;
            newFinalDue = amountDue.subtract(creditBalance);
        }

        // Step 5 - Deduct credit if any was used
        if (creditUsed.compareTo(BigDecimal.ZERO) > 0) {
            memberCreditService.deductCredit(user.getId(), creditUsed);
        }

        // Step 6 - Update payment record
        paymentRecord.setPaymentMode(request.getPaymentMode());
        paymentRecord.setTransactionRef(request.getTransactionRef());
        paymentRecord.setScreenshotUrl(request.getScreenshotUrl());
        paymentRecord.setCreditUsed(creditUsed);
        paymentRecord.setFinalDue(newFinalDue);
        paymentRecord.setStatus("UNDER_REVIEW");
        paymentRecord.setPaymentDate(LocalDate.now());

        PaymentRecord savedRecord = paymentRecordRepository.save(paymentRecord);

        // Step 7 - Build response directly
        PaymentResponse response = new PaymentResponse();
        response.setId(savedRecord.getId());
        response.setCycleId(savedRecord.getCycle().getId());
        response.setMonth(savedRecord.getCycle().getMonth());
        response.setYear(savedRecord.getCycle().getYear());
        response.setUserId(savedRecord.getUser().getId());
        response.setMemberName(savedRecord.getUser().getName());
        response.setFlatNumber(savedRecord.getUser().getFlatNumber());
        response.setPaidAmount(savedRecord.getPaidAmount());
        response.setCreditUsed(savedRecord.getCreditUsed());
        response.setFinalDue(savedRecord.getFinalDue());
        response.setPaymentMode(savedRecord.getPaymentMode());
        response.setTransactionRef(savedRecord.getTransactionRef());
        response.setScreenshotUrl(savedRecord.getScreenshotUrl());
        response.setStatus(savedRecord.getStatus());
        response.setPaymentDate(savedRecord.getPaymentDate());
        response.setCreatedAt(savedRecord.getCreatedAt());

        return response;
    }

    @Override
    public PaymentResponse approvePayment(Long paymentId) {

        // Step 1 - Find payment record
        PaymentRecord record = paymentRecordRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        // Step 2 - Check if under review
        if (!record.getStatus().equals("UNDER_REVIEW")) {
            throw new BadRequestException("Payment is not under review!");
        }

        // Step 3 - Approve payment
        record.setPaidAmount(record.getFinalDue());//paid amount same as final dues means this amount is paid

        record.setStatus("PAID");

        PaymentRecord savedRecord = paymentRecordRepository.save(record);

        // Step 4 - Build response
        PaymentResponse response = new PaymentResponse();
        response.setId(savedRecord.getId());
        response.setCycleId(savedRecord.getCycle().getId());
        response.setMonth(savedRecord.getCycle().getMonth());
        response.setYear(savedRecord.getCycle().getYear());
        response.setUserId(savedRecord.getUser().getId());
        response.setMemberName(savedRecord.getUser().getName());
        response.setFlatNumber(savedRecord.getUser().getFlatNumber());
        response.setPaidAmount(savedRecord.getPaidAmount());
        response.setCreditUsed(savedRecord.getCreditUsed());
        response.setFinalDue(savedRecord.getFinalDue());
        response.setPaymentMode(savedRecord.getPaymentMode());
        response.setTransactionRef(savedRecord.getTransactionRef());
        response.setScreenshotUrl(savedRecord.getScreenshotUrl());
        response.setStatus(savedRecord.getStatus());
        response.setPaymentDate(savedRecord.getPaymentDate());
        response.setCreatedAt(savedRecord.getCreatedAt());

        return response;
    }

    @Override
    @Transactional
    public PaymentResponse rejectPayment(Long paymentId) {

        // Step 1 - Find payment record
        PaymentRecord record = paymentRecordRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        // Step 2 - Check if under review
        if (!record.getStatus().equals("UNDER_REVIEW")) {
            throw new BadRequestException("Payment is not under review!");
        }

        // Step 3 - Give back credit that was deducted during upload
        if (record.getCreditUsed().compareTo(BigDecimal.ZERO) > 0) {
            memberCreditService.addCredit(
                    record.getUser().getId(), record.getCreditUsed());
        }

        // Step 4 - Reset record back to pending
        record.setStatus("PENDING");
        record.setPaymentMode(null);
        record.setTransactionRef(null);
        record.setScreenshotUrl(null);
        record.setPaymentDate(null);
        record.setCreditUsed(BigDecimal.ZERO);

        PaymentRecord savedRecord = paymentRecordRepository.save(record);

        // Step 5 - Build response
        PaymentResponse response = new PaymentResponse();
        response.setId(savedRecord.getId());
        response.setCycleId(savedRecord.getCycle().getId());
        response.setMonth(savedRecord.getCycle().getMonth());
        response.setYear(savedRecord.getCycle().getYear());
        response.setUserId(savedRecord.getUser().getId());
        response.setMemberName(savedRecord.getUser().getName());
        response.setFlatNumber(savedRecord.getUser().getFlatNumber());
        response.setPaidAmount(savedRecord.getPaidAmount());
        response.setCreditUsed(savedRecord.getCreditUsed());
        response.setFinalDue(savedRecord.getFinalDue());
        response.setPaymentMode(savedRecord.getPaymentMode());
        response.setTransactionRef(savedRecord.getTransactionRef());
        response.setScreenshotUrl(savedRecord.getScreenshotUrl());
        response.setStatus(savedRecord.getStatus());
        response.setPaymentDate(savedRecord.getPaymentDate());
        response.setCreatedAt(savedRecord.getCreatedAt());

        return response;
    }

    @Override
    public List<PaymentResponse> getPaymentsByCycle(Long cycleId) {

        List<PaymentRecord> records =
                paymentRecordRepository.findByCycleId(cycleId);
        List<PaymentResponse> responseList = new ArrayList<>();

        for (PaymentRecord record : records) {
            PaymentResponse response = new PaymentResponse();
            response.setId(record.getId());
            response.setCycleId(record.getCycle().getId());
            response.setMonth(record.getCycle().getMonth());
            response.setYear(record.getCycle().getYear());
            response.setUserId(record.getUser().getId());
            response.setMemberName(record.getUser().getName());
            response.setFlatNumber(record.getUser().getFlatNumber());
            response.setPaidAmount(record.getPaidAmount());
            response.setCreditUsed(record.getCreditUsed());
            response.setFinalDue(record.getFinalDue());
            response.setPaymentMode(record.getPaymentMode());
            response.setTransactionRef(record.getTransactionRef());
            response.setScreenshotUrl(record.getScreenshotUrl());
            response.setStatus(record.getStatus());
            response.setPaymentDate(record.getPaymentDate());
            response.setCreatedAt(record.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User member = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<PaymentRecord> records =
                paymentRecordRepository.findByUserId(member.getId());
        List<PaymentResponse> responseList = new ArrayList<>();

        for (PaymentRecord record : records) {
            PaymentResponse response = new PaymentResponse();
            response.setId(record.getId());
            response.setCycleId(record.getCycle().getId());
            response.setMonth(record.getCycle().getMonth());
            response.setYear(record.getCycle().getYear());
            response.setUserId(record.getUser().getId());
            response.setMemberName(record.getUser().getName());
            response.setFlatNumber(record.getUser().getFlatNumber());
            response.setPaidAmount(record.getPaidAmount());
            response.setCreditUsed(record.getCreditUsed());
            response.setFinalDue(record.getFinalDue());
            response.setPaymentMode(record.getPaymentMode());
            response.setTransactionRef(record.getTransactionRef());
            response.setScreenshotUrl(record.getScreenshotUrl());
            response.setStatus(record.getStatus());
            response.setPaymentDate(record.getPaymentDate());
            response.setCreatedAt(record.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }
}