package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.PaymentResponse;
import com.Simran.apartmentmanager.dto.PaymentUploadRequest;

import java.util.List;

public interface PaymentService {

    // Member uploads payment screenshot
    PaymentResponse uploadPayment(PaymentUploadRequest request);

    // Pradhana approves payment
    PaymentResponse approvePayment(Long paymentId);

    // Pradhana rejects payment
    PaymentResponse rejectPayment(Long paymentId);

    // Pradhana views all payments for a cycle
    List<PaymentResponse> getPaymentsByCycle(Long cycleId);

    // Member views own payments
    List<PaymentResponse> getMyPayments();
}
