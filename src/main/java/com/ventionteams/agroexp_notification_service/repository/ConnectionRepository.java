package com.ventionteams.agroexp_notification_service.repository;

import com.ventionteams.agroexp_notification_service.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, UUID> {
  boolean existsByUserId(UUID userId);
}
