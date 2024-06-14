package com.ventionteams.agroexp_notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.controller.SSEController;
import com.ventionteams.agroexp_notification_service.exception.JsonIOException;
import com.ventionteams.agroexp_notification_service.model.Connection;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@EnableScheduling
@RequiredArgsConstructor
@RequestMapping("/sse")
public class SSEService {

  private final ObjectMapper objectMapper;

  private final SSEController sseController;
  private final ConnectionService connectionService;

  public void send(NotificationPayload notificationPayload) {
    var subscriptions = sseController.getSubscriptions();

    subscriptions
        .values()
        .forEach(
            (subscription -> {
              ServerSentEvent<String> event;
              // TODO: finish implementation (small draft of possible usage
//              Connection connection = new Connection();
//              connection.setId(UUID.randomUUID());
//              connection.setUserId(notificationPayload.getUserID());
//              connection.setInstanceId(UUID.fromString("8f248888-ab25-469b-982c-87b36efc2f64"));
//              connectionService.saveConnection(connection);
              try {
                event =
                    ServerSentEvent.builder(objectMapper.writeValueAsString(notificationPayload))
                        .build();
              } catch (JsonProcessingException e) {
                throw new JsonIOException("Error while bet sending");
              }
              if (subscription.getUserId().equals(notificationPayload.getUserID())) {
                subscription.getFluxSink().next(event);
              }
            }));
  }
}
