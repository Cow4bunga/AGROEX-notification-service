package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.EmailService;
import com.ventionteams.agroexp_notification_service.service.NotificationTypeResolverService;
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
  private final SnsService snsService;
  private final NotificationTypeResolverService notificationTypeResolverService;

  @SqsListener(value = "Agroex-notification-queue")
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    log.info(String.valueOf(notificationPayload));
    notificationTypeResolverService.processNotification(notificationPayload);

    // TODO: remove next line when done with Redis
    emailService.send(notificationPayload);
  }

  @SqsListener(value = "Agroex-SSE-notification-queue")
  public void acceptBroadcast(@Payload NotificationPayload notificationPayload, Map<String,Object> headers){
    // TODO: add logic
  }
}
