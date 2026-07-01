package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.PaymentResponse;
import com.Simran.apartmentmanager.dto.PaymentUploadRequest;
import com.Simran.apartmentmanager.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // Member uploads payment
    @PostMapping("/upload")
    public ResponseEntity<PaymentResponse> uploadPayment(
            @Valid @RequestBody PaymentUploadRequest request) {
        return new ResponseEntity<>(paymentService.uploadPayment(request),
                HttpStatus.OK);
    }

    // Pradhana approves payment
    @PutMapping("/{id}/approve")
    public ResponseEntity<PaymentResponse> approvePayment(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                paymentService.approvePayment(id),
                HttpStatus.OK);
    }

    // Pradhana rejects payment
    @PutMapping("/{id}/reject")
    public ResponseEntity<PaymentResponse> rejectPayment(
            @PathVariable Long id) {
        return new ResponseEntity<>(
                paymentService.rejectPayment(id),
                HttpStatus.OK);
    }

    // Pradhana views all payments for a cycle
    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<  List<PaymentResponse>>
    getPaymentsByCycle(@PathVariable Long cycleId) {
        return new ResponseEntity<>(
                paymentService.getPaymentsByCycle(cycleId),
                HttpStatus.OK);
    }

    // Member views own payments
    @GetMapping("/my")
    public ResponseEntity<List<PaymentResponse>>
    getMyPayments() {
        return new ResponseEntity<>(
                paymentService.getMyPayments(),
                HttpStatus.OK);
    }

}
