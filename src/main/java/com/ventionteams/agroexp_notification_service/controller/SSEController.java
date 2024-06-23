package com.ventionteams.agroexp_notification_service.controller;

import com.ventionteams.agroexp_notification_service.model.UserConnection;
import com.ventionteams.agroexp_notification_service.model.SSESubscription;
import com.ventionteams.agroexp_notification_service.service.SSEService;
import com.ventionteams.agroexp_notification_service.service.UserConnectionService;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Slf4j
@RestController
@RequestMapping("/sse")
public class SSEController {
  private final UserConnectionService userConnectionService;

  @GetMapping(path = "/connect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent> openSseStream(@PathVariable UUID userId) {
    var subscriptions = SSEService.getSubscriptions();
    var connectionId = UUID.randomUUID();
    return Flux.create(
        fluxSink -> {
          fluxSink.onCancel(
              () -> {
                subscriptions.remove(connectionId);
                userConnectionService.deleteById(connectionId);
                log.info("Subscription for user with id {} was closed", userId);
              });
          var successfullyConnectedEvent =
              ServerSentEvent.builder("Connected successfully").build();
          fluxSink.next(successfullyConnectedEvent);
          subscriptions.put(connectionId, new SSESubscription(userId, fluxSink));
          userConnectionService.save(new UserConnection(connectionId, userId));

          log.info("Created subscription for user with id: {}",userId);
        });
  }
}
