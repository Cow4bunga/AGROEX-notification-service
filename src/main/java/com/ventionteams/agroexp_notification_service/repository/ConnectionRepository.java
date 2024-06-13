package com.ventionteams.agroexp_notification_service.repository;

import com.ventionteams.agroexp_notification_service.model.Connection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
  Optional<Connection> findByUserId(UUID userId);

  boolean existsByUserId(UUID userId);
}
