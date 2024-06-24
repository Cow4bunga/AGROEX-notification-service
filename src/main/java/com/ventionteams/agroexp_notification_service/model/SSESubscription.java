package com.ventionteams.agroexp_notification_service.model;

import lombok.Data;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

import java.io.Serializable;
import java.util.UUID;

@Data
public final class SSESubscription<T> implements Serializable {
  private final UUID userId;
  private final FluxSink<ServerSentEvent<T>> fluxSink;
}
