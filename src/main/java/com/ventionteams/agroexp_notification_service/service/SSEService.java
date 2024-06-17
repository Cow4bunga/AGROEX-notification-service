package com.ventionteams.agroexp_notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.controller.SSEController;
import com.ventionteams.agroexp_notification_service.exception.JsonIOException;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableScheduling
@RequiredArgsConstructor
@RequestMapping("/sse")
public class SSEService {

  private final ObjectMapper objectMapper;

  public void send(NotificationPayload notificationPayload) {
    var subscriptions = SSEController.getSubscriptions();
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
