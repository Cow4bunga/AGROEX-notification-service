package com.ventionteams.agroexp_notification_service.service.messaging.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      log.info("New message received: {}", message);
      NotificationPayload payload =
          objectMapper.readValue(message.getBody(), NotificationPayload.class);
    } catch (IOException e) {
      log.error("error while parsing message");
    }
  }
}
