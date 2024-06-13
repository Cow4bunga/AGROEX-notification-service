package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.repository.ConnectionRepository;
import com.ventionteams.agroexp_notification_service.service.messaging.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTypeResolverService {
  private final SSEService sseService;
  private final EmailService emailService;
  private final ConnectionService connectionService;
  private final MessagePublisher messagePublisher;

  public void processNotification(NotificationPayload notificationPayload) {
    var connection = connectionService.getByUserId(notificationPayload.getUserID());
    if (connection == null) {
      emailService.send(notificationPayload);
      return;
    }

    // TODO: here we need to implement instance ID check
    if (connection
        .getInstanceId()
        .equals(UUID.fromString("8f248888-ab25-469b-982c-87b36efc2f64"))) {
      // sseService.send(notificationPayload);
      System.out.println("success");
    } else {
      messagePublisher.publish(notificationPayload);
    }
  }
}
