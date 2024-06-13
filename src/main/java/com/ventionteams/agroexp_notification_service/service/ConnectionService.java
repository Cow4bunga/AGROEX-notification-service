package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.exception.ConnectionNotFoundException;
import com.ventionteams.agroexp_notification_service.model.Connection;
import com.ventionteams.agroexp_notification_service.model.NotificationPayload;
import com.ventionteams.agroexp_notification_service.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionService {
  private final ConnectionRepository connectionRepository;

  public void saveConnection(NotificationPayload notificationPayload) {
    Connection connection = new Connection();
    connection.setId(UUID.randomUUID());
    connection.setUserId(notificationPayload.getUserID());
    connection.setInstanceId(UUID.fromString("8f248888-ab25-469b-982c-87b36efc2f64"));
    connectionRepository.save(connection);
  }

  public Connection getByUserId(UUID userId) {
    return connectionRepository.findByUserId(userId).orElse(null);
  }
}
