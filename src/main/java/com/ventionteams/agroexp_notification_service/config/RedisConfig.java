package com.ventionteams.agroexp_notification_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
  @Value("${spring.redis.topic}")
  private String redisPubSubTopic;

  @Bean
  public ChannelTopic topic() {
    return new ChannelTopic(redisPubSubTopic);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }
}
