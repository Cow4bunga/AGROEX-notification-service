package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsService {
  private final NotificationMessagingTemplate notificationMessagingTemplate;

  // TODO: add convert
  public <T> void sendSnsMessage(String topic, Object message, Map<String, Object> headers) {
    if (message == null) {
      return;
    }
    Map<String, Object> headers1 = new HashMap<>();
    notificationMessagingTemplate.sendNotification(topic, message, "subject");
  }
}
