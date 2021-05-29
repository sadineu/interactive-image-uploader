package com.example.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {
    @Autowired
    private SimpMessagingTemplate template;
    public String sendNotification() {
        template.convertAndSend("/topic/notification", 1);
        return "Notifications successfully sent to Angular !";
    }
}
