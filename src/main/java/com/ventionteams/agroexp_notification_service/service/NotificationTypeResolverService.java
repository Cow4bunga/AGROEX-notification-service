package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.messaging.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTypeResolverService {
  private final EmailService emailService;
  private final UserConnectionService userConnectionService;
  private final MessagePublisher messagePublisher;

  public void processNotification(NotificationPayload notificationPayload) {
    var connection = userConnectionService.getByUserId(notificationPayload.getUserID());
    if (connection.isEmpty()) {
      emailService.send(notificationPayload);
    } else {
      messagePublisher.publish(notificationPayload);
    }
  }
}
