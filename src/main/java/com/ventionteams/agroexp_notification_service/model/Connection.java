package com.ventionteams.agroexp_notification_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@RedisHash("connections")
public class Connection {
  @Id private UUID id;

  @Indexed private UUID userId;
  @Indexed private UUID instanceId;
}
