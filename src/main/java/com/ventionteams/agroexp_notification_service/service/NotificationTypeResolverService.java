package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTypeResolverService {
  private final SSEService sseService;
  private final EmailService emailService;
  private final ConnectionRepository connectionRepository;

  // TODO: add broadcast for all instances
  public void processNotification(NotificationPayload notificationPayload) {
    if (connectionRepository.existsByUserId(notificationPayload.getUserID())) {
      sseService.send(notificationPayload);
    } else {
      emailService.send(notificationPayload);
    }
  }
}
