package com.ventionteams.agroexp_notification_service.config;

import com.ventionteams.agroexp_notification_service.config.properties.SQSProperties;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SQSConfig {
  @Bean
  SqsAsyncClient sqsAsyncClient(SQSProperties sqsProperties) {
    return SqsAsyncClient.builder()
        .region(Region.of(sqsProperties.getRegion()))
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .build();
  }

  @Bean
  public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
    return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
  }
}
