package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.EmailService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageReceiver {

  private final EmailService emailService;

  @SqsListener(
      value = "Agroex-notification-queue",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    emailService.send(notificationPayload);
  }
}
