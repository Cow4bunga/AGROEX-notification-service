package com.ventionteams.agroexp_notification_service.model;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationPayload {
    private UUID userID;
    private String lotTitle;
    private String email;
    private EventType event;
}
