package com.reliaquest.api.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ResilientWebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public CircuitBreaker mockEmployeeCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // % of failures to open circuit
                .waitDurationInOpenState(Duration.ofSeconds(30)) // wait before trying again
                .slidingWindowSize(20) // # of calls to calculate failure rate
                .build();

        return CircuitBreaker.of("mockEmployeeCircuitBreaker", config);
    }

    @Bean
    public RateLimiter mockEmployeeRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(10) // 5 calls per second
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();

        return RateLimiter.of("mockEmployeeRateLimiter", config);
    }
}
