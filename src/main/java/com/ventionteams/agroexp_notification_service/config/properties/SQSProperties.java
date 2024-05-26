package com.ventionteams.agroexp_notification_service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "aws.sqs")
@Component
public class SQSProperties {}
