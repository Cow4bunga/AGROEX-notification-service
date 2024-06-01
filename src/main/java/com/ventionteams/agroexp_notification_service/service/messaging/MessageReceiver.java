package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.EmailService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {

  private final EmailService emailService;

  @SqsListener(value = "Agroex-notification-queue")
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    log.debug(String.valueOf(notificationPayload));
    log.info(String.valueOf(notificationPayload));
    emailService.send(notificationPayload);
  }
}
