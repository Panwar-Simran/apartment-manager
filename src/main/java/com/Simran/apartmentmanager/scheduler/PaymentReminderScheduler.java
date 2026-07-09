package com.Simran.apartmentmanager.scheduler;

import com.Simran.apartmentmanager.entity.MaintenanceCycle;
import com.Simran.apartmentmanager.entity.PaymentRecord;
import com.Simran.apartmentmanager.repository.MaintenanceCycleRepository;
import com.Simran.apartmentmanager.repository.PaymentRecordRepository;
import com.Simran.apartmentmanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PaymentReminderScheduler {

    @Autowired
    private MaintenanceCycleRepository maintenanceCycleRepository;

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    @Autowired
    private EmailService emailService;

    // Runs every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void sendPaymentReminders() {

        // Step 1 - Get current month and year
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        // Step 2 - Find maintenance cycle for current month
        MaintenanceCycle cycle = maintenanceCycleRepository
                .findByMonthAndYear(currentMonth, currentYear)
                .orElse(null);

        // Step 3 - If no cycle exists, stop
        if (cycle == null) {
            return;
        }

        // Step 4 - Find all pending payment records
        List<PaymentRecord> pendingRecords =
                paymentRecordRepository.findByCycleIdAndStatus(
                        cycle.getId(), "PENDING");

        // Step 5 - Send reminder email to every pending member
        for (PaymentRecord record : pendingRecords) {

            emailService.sendPaymentReminderEmail(
                    record.getUser().getEmail(),
                    record.getUser().getName(),
                    cycle.getAmountPerMember(),
                    cycle.getDueDate()
            );
        }//for
    }//func
}