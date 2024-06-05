package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTypeResolverService {
  private final SSEService sseService;
  private final EmailService emailService;

  public void processNotification(NotificationPayload notificationPayload) {
    emailService.send(notificationPayload);
  }
}
