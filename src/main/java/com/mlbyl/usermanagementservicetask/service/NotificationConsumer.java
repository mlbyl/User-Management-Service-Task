package com.mlbyl.usermanagementservicetask.service;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class NotificationConsumer {
    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-registered", groupId = "notification-group-v2")
    public void consumeUserRegisteredEvent(String emailAdress) {
        emailService.sendWelcomeEmail(emailAdress);
    }
}
