package com.ventionteams.agroexp_notification_service.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String errorMessage) {}
