package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.exception.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendWelcomeEmail(String toEmail, String name, String tempPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true);

            helper.setTo(toEmail);
            helper.setSubject("Welcome to Apartment Manager!");
            helper.setText(
                    "Dear " + name + ",\n\n" +
                            "You have been added to the Apartment Manager system.\n\n" +
                            "Your login credentials:\n" +
                            "Email: " + toEmail + "\n" +
                            "Temporary Password: " + tempPassword + "\n\n" +
                            "Please login and change your password immediately.\n\n" +
                            "Login URL: http://localhost:8080/login\n\n" +
                            "Regards,\n" +
                            "Apartment Manager Team",
                    false
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new EmailSendingException(
                    "Failed to send welcome email to " + toEmail);
        }
    }


    @Override
    public void sendPaymentReminderEmail(String toEmail, String name, BigDecimal amount, LocalDate dueDate) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true);

            helper.setTo(toEmail);
            helper.setSubject("Payment Reminder - Maintenance Due!");
            helper.setText(
                    "Dear " + name + ",\n\n" +
                            "This is a reminder that your monthly maintenance " +
                            "payment is still pending.\n\n" +
                            "Amount Due: ₹" + amount + "\n" +
                            "Due Date: " + dueDate + "\n\n" +
                            "Please pay at the earliest and upload your " +
                            "payment screenshot.\n\n" +
                            "Regards,\n" +
                            "Apartment Manager Team",
                    false
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new EmailSendingException(
                    "Failed to send reminder email to " + toEmail);
        }
    }
}
