package com.mlbyl.usermanagementservicetask.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
