package com.ventionteams.agroexp_notification_service.util;

import java.util.UUID;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class AuthUtil {

  public static Mono<Boolean> isSameUser(UUID id) {
    return getAuthenticatedUserId()
            .map(authUserId -> authUserId.equals(id));
  }

  public static Mono<UUID> getAuthenticatedUserId() {
    return ReactiveSecurityContextHolder.getContext()
            .flatMap(context -> Mono.justOrEmpty(context.getAuthentication()))
            .map(auth -> UUID.fromString(auth.getName().toString()));
  }
}
