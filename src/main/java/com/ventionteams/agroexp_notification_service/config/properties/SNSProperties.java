package com.ventionteams.agroexp_notification_service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spring.cloud.aws.sqs")
@Component
public class SNSProperties {
  private String accessKey;
  private String secretKey;
  private String region;
}