package com.Simran.apartmentmanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String name, String tempPassword);

    // Sends payment reminder to members with PENDING status
    void sendPaymentReminderEmail(String toEmail, String name,
                                  BigDecimal amount,
                                  LocalDate dueDate);
}
