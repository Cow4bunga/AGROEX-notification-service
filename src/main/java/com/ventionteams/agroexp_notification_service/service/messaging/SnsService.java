package com.ventionteams.agroexp_notification_service.service.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsService {
  private final NotificationMessagingTemplate notificationMessagingTemplate;

  public void sendSnsMessage(String topic, String message, String subject) {
    notificationMessagingTemplate.sendNotification(topic, message, subject);
  }
}
