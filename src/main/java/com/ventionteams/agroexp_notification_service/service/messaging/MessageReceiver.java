package com.ventionteams.agroexp_notification_service.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageReceiver {

  private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
  private final EmailService emailService;

  @SqsListener(
      value = "Agroex-notification-queue",
      deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void listen(
      @Payload NotificationPayload notificationPayload, @Headers Map<String, Object> headers) {
    System.out.println(notificationPayload);
      emailService.send(notificationPayload);
  }
}
