package com.ventionteams.agroexp_notification_service.service;

import com.ventionteams.agroexp_notification_service.model.UserConnection;
import com.ventionteams.agroexp_notification_service.repository.UserConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserConnectionService {
  private final UserConnectionRepository userConnectionRepository;

  public Iterable<UserConnection> getAll() {
    return userConnectionRepository.findAll();
  }

  public void save(UserConnection userConnection) {
    userConnectionRepository.save(userConnection);
  }

  public Optional<UserConnection> getByUserId(UUID userId) {
    return userConnectionRepository.findByUserId(userId);
  }

  public void deleteById(UUID userId) {
    userConnectionRepository.deleteById(userId);
  }

  public void deleteAll() {
    userConnectionRepository.deleteAll();
  }
}
