package com.ventionteams.agroexp_notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.exception.JsonIOException;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.model.SSESubscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SSEService {

  @Getter private static Map<UUID, SSESubscription> subscriptions = new HashMap<>();
  private final ObjectMapper objectMapper;

  public void send(NotificationPayload notificationPayload) {
    subscriptions
        .values()
        .forEach(
            (subscription -> {
              ServerSentEvent<String> event;
              if (subscription.getUserId().equals(notificationPayload.getUserID())) {
                try {
                  event =
                      ServerSentEvent.builder(objectMapper.writeValueAsString(notificationPayload))
                          .build();
                } catch (JsonProcessingException e) {
                  throw new JsonIOException("Error while bet sending");
                }
                subscription.getFluxSink().next(event);
              }
            }));
  }
}
