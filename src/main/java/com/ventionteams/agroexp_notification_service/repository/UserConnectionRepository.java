package com.ventionteams.agroexp_notification_service.repository;

import com.ventionteams.agroexp_notification_service.model.UserConnection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserConnectionRepository extends CrudRepository<UserConnection, UUID> {
  Optional<UserConnection> findByUserId(UUID userId);
  void deleteAll();
}
