package com.Simran.apartmentmanager.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String name, String tempPassword);
}
