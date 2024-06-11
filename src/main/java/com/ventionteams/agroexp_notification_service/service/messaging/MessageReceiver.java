package com.ventionteams.agroexp_notification_service.service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.EmailService;
import com.ventionteams.agroexp_notification_service.service.NotificationTypeResolverService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {

  private final EmailService emailService;
  private final SnsService snsService;
  private final NotificationTypeResolverService notificationTypeResolverService;
  private static final String TOPIC = "Agroex-notification-topic";

  @SqsListener(value = "Agroex-notification-queue")
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    log.info(String.valueOf(notificationPayload));
    //    notificationTypeResolverService.processNotification(notificationPayload);
    emailService.send(notificationPayload);
  }
}
