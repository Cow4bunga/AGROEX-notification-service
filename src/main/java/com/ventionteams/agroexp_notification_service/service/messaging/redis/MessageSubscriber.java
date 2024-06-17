package com.ventionteams.agroexp_notification_service.service.messaging.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.controller.SSEController;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.SSEService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final SSEService sseService;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      log.info("New message received: {}", message);
      var payload = objectMapper.readValue(message.getBody(), NotificationPayload.class);
      var userId = payload.getUserID();
      SSEController.getSubscriptions().values().stream()
          .filter(sseSubscription -> sseSubscription.getUserId().equals(userId))
          .forEach(sseSubscription -> sseService.send(payload));
    } catch (IOException e) {
      log.error("Unable to process message!");
    }
  }
}
