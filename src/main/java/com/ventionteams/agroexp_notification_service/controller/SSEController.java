package com.ventionteams.agroexp_notification_service.controller;

import com.ventionteams.agroexp_notification_service.model.SSESubscription;
import lombok.Data;
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

    private Map<UUID, SSESubscription> subscriptions = new HashMap<>();

    @GetMapping(path = "/connect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> openSseStream(@PathVariable UUID userId) {

        return Flux.create(fluxSink -> {
            fluxSink.onCancel(() -> {
                subscriptions.remove(userId);
                log.info(String.format("Subscription for user with id %s was closed", userId));
            });
            var successfullyConnectedEvent = ServerSentEvent.builder("Connected successfully").build();
            fluxSink.next(successfullyConnectedEvent);
            subscriptions.put(userId, new SSESubscription(userId, fluxSink));

            log.info(String.format("Created subscription for user with id: %s", userId));
        });
    }
}