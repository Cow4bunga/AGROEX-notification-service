package com.ventionteams.agroexp_notification_service.service.messaging.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.model.Connection;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.repository.ConnectionRepository;
import com.ventionteams.agroexp_notification_service.service.ConnectionService;
import com.ventionteams.agroexp_notification_service.service.NotificationTypeResolverService;
import com.ventionteams.agroexp_notification_service.service.SSEService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final ConnectionService connectionService;
  private final SSEService sseService;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      log.info("New message received: {}", message);
      NotificationPayload payload =
          objectMapper.readValue(message.getBody(), NotificationPayload.class);
      var connection = connectionService.getByUserId(payload.getUserID());
//      if (connection
//          .getInstanceId()
//          .equals(UUID.fromString("8f248888-ab25-469b-982c-87b36efc2f64"))) {
//        System.out.println(payload);
//      }
    } catch (IOException e) {
      log.error("Unable to process message!");
    }
  }
}
