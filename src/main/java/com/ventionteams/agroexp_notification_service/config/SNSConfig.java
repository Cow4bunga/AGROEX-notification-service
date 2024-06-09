package com.ventionteams.agroexp_notification_service.config;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.ventionteams.agroexp_notification_service.config.properties.SNSProperties;
import com.ventionteams.agroexp_notification_service.service.messaging.CustomMessageConverter;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.regions.Region;

@Configuration
public class SNSConfig {
  @Bean
  @Primary
  public AmazonSNSAsync amazonSNSAsync(SNSProperties snsProperties) {
    return AmazonSNSAsyncClientBuilder.standard()
        .withRegion(String.valueOf(Region.of(snsProperties.getRegion())))
        .build();
  }

  @Bean
  public NotificationMessagingTemplate notificationMessagingTemplate(
      AmazonSNSAsync amazonSNSAsync, CustomMessageConverter customMessageConverter) {
    NotificationMessagingTemplate notificationMessagingTemplate =
        new NotificationMessagingTemplate(amazonSNSAsync);
    notificationMessagingTemplate.setMessageConverter(customMessageConverter);
    return notificationMessagingTemplate;
  }
}
