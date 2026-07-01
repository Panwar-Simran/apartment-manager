package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    // Find all payments for a cycle
    List<PaymentRecord> findByCycleId(Long cycleId);

    // Find all payments for a member
    List<PaymentRecord> findByUserId(Long userId);

    // Find specific payment for member in a cycle
    Optional<PaymentRecord> findByCycleIdAndUserId(
            Long cycleId, Long userId);

    // Find all pending payments for a cycle
    List<PaymentRecord> findByCycleIdAndStatus(
            Long cycleId, String status);
}
