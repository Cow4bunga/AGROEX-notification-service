package com.ventionteams.agroexp_notification_service.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventionteams.agroexp_notification_service.config.properties.SQSProperties;
import com.ventionteams.agroexp_notification_service.service.messaging.CustomMessageConverter;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import software.amazon.awssdk.regions.Region;

import java.util.List;

@Configuration
public class SQSConfig {
  @Bean
  @Primary
  public AmazonSQSAsync amazonSQSAsync(SQSProperties sqsProperties) {
    return AmazonSQSAsyncClientBuilder.standard()
        .withRegion(String.valueOf(Region.of(sqsProperties.getRegion())))
        .build();
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(
      AmazonSQSAsync amazonSQSAsync, CustomMessageConverter customMessageConverter) {
    QueueMessagingTemplate queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    queueMessagingTemplate.setMessageConverter(customMessageConverter);
    return queueMessagingTemplate;
  }

  @Bean
  public QueueMessageHandlerFactory queueMessageHandlerFactory(
      ObjectMapper objectMapper, AmazonSQSAsync amazonSQSAsync) {
    MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setObjectMapper(objectMapper);
    messageConverter.setStrictContentTypeMatch(false);

    QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
    factory.setAmazonSqs(amazonSQSAsync);

    List<HandlerMethodArgumentResolver> resolvers =
        List.of(new PayloadMethodArgumentResolver(messageConverter, null, false));
    factory.setArgumentResolvers(resolvers);

    return factory;
  }
}
