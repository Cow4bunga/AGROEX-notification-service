package com.ventionteams.agroexp_notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@RedisHash("connections")
@AllArgsConstructor
public class UserConnection {
  @Id private UUID id;

  @Indexed private UUID userId;
}