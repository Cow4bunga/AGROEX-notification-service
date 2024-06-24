package com.ventionteams.agroexp_notification_service.service.messaging.redis;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

  public void publish(NotificationPayload notificationPayload) {
    log.info("Sending message: {}", notificationPayload);
    redisTemplate.convertAndSend(channelTopic.getTopic(), notificationPayload);
  }
}
