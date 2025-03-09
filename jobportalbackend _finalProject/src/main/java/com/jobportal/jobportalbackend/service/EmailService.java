package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String uuid,String email) {
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + uuid;
        System.out.println("Sending email...");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Test Email");
            message.setText("Hello, this is a test email from Spring Boot." + verificationLink);

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

}