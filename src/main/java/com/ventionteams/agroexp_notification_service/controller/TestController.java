package com.ventionteams.agroexp_notification_service.controller;

import com.ventionteams.agroexp_notification_service.model.Connection;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.ConnectionService;
import com.ventionteams.agroexp_notification_service.service.messaging.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  private final MessagePublisher publisher;
  private final ConnectionService connectionService;

  @PostMapping
  public ResponseEntity<Void> publish(@RequestBody NotificationPayload notificationPayload) {
    publisher.publish(notificationPayload);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Connection> getConnectionByUserId(@PathVariable UUID id) {
    return new ResponseEntity<>(connectionService.getByUserId(id).get(), HttpStatus.OK);
  }
}
