package com.ventionteams.agroexp_notification_service.service.messaging;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
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

  private final NotificationTypeResolverService notificationTypeResolverService;

  @SqsListener(value = "${spring.cloud.aws.sqs.name}")
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    log.info("Received payload: {}", notificationPayload);
    notificationTypeResolverService.processNotification(notificationPayload);
  }
}
