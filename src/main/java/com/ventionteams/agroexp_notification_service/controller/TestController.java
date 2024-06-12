package com.ventionteams.agroexp_notification_service.controller;

import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.service.messaging.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  private final MessagePublisher publisher;

  @PostMapping
  public ResponseEntity<Void> publish(@RequestBody NotificationPayload notificationPayload) {
    publisher.publish(notificationPayload);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
