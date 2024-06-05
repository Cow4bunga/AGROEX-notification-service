package com.ventionteams.agroexp_notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPayload {
  private UUID userID;
  private String lotTitle;
  private String email;
  private EventType event;
}
