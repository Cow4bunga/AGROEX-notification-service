package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.Connection;
import com.ventionteams.agroexp_notification_service.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionService {
  private final ConnectionRepository connectionRepository;

  public Iterable<Connection> getAll() {
    return connectionRepository.findAll();
  }

  public void save(Connection connection) {
    connectionRepository.save(connection);
  }

  public Optional<Connection> getByUserId(UUID userId) {
    return connectionRepository.findByUserId(userId);
  }

  public void deleteById(UUID userId) {
    connectionRepository.deleteById(userId);
  }

  public void deleteAll() {
    connectionRepository.deleteAll();
  }
}
