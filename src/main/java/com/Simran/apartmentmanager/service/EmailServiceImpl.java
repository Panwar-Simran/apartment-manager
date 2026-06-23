package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.exception.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;



    @Override
    public void sendWelcomeEmail(String toEmail, String name, String tempPassword) {
       try{
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

       }
       catch(Exception ex)
       {
           throw new EmailSendingException( "Failed to send email to " + toEmail);
       }
    }
}
