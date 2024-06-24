package com.ventionteams.agroexp_notification_service.security;

import com.ventionteams.agroexp_notification_service.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private static final String ADMIN = String.valueOf(Role.ADMIN);
  private static final String USER = String.valueOf(Role.USER);

  @Value("${cors.origins}")
  String[] origins;

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.setAllowedOrigins(List.of(origins));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsWebFilter(source);
  }

  @Bean
  public SecurityWebFilterChain baseSecurityFilterChain(ServerHttpSecurity http) {
    return http.securityMatcher(ServerWebExchangeMatchers.anyExchange())
        .csrf(csrf -> csrf.disable())
        .authorizeExchange(
            authorize ->
                authorize
                    .pathMatchers("/sse/**", "/test/**")
                    .hasAnyRole(ADMIN, USER)
                    .anyExchange()
                    .authenticated())
        .oauth2ResourceServer(
            oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(this::customConverter)))
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .build();
  }

  private Mono<AbstractAuthenticationToken> customConverter(Jwt jwt) {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("cognito:groups");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return Mono.justOrEmpty(jwtAuthenticationConverter.convert(jwt));
  }
}
