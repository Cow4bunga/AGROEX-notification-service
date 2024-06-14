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

  public void saveConnection(Connection connection) {
    connectionRepository.save(connection);
  }

  public Connection getByUserId(UUID userId) {
    return connectionRepository.findByUserId(userId).orElse(null);
  }

  public void deleteByUserId(UUID userId) {
    connectionRepository.deleteByUserId(userId);
  }
}
