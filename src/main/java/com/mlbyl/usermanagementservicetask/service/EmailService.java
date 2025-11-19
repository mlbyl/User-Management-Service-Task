package com.mlbyl.usermanagementservicetask.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendWelcomeEmail(String toEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("mahammadalibayli@gmail.com");
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("Welcome");
        simpleMailMessage.setText("We're so glad you've joined us. Your account has been created.");

        javaMailSender.send(simpleMailMessage);

    }
}
