package com.reliaquest.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.reliaquest.api.exceptions.BaseException;
import com.reliaquest.api.exceptions.RateLimitException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Service
public class ExternalApiService {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    WebClient webClient;

    @Autowired
    CircuitBreaker circuitBreaker;

    @Autowired
    RateLimiter rateLimiter;

    public Object buildRequestAndReturnResponse(
            String endpointUrl,
            Class<?> returnObject,
            HttpMethod httpMethodName,
            Object postObject,
            HttpHeaders headers,
            Map<String, Object> uriVariables) {
        Object response;
        URI uri = UriComponentsBuilder.fromUriString(endpointUrl)
                .uriVariables(uriVariables)
                .build()
                .toUri();

        Supplier<Mono<?>> supplier = () -> webClient
                .method(httpMethodName)
                .uri(uri)
                .body(BodyInserters.fromObject(postObject != null ? postObject : new HashMap<>()))
                .headers(headerHeaders1 -> {
                    headerHeaders1.addAll(headers);
                })
                .retrieve()
                .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, clientResponse -> {
                    throw new RateLimitException("Rate limit exceeded");
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> handleErrors(clientResponse, endpointUrl))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> handleErrors(clientResponse, endpointUrl))
                .bodyToMono(returnObject);

        // Decorate with CircuitBreaker and RateLimiter
        Supplier<Mono<?>> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRateLimiter(rateLimiter)
                .decorate();

        // Add Retry with backoff and jitter
        return Mono.defer(decoratedSupplier)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .jitter(0.5) // Added jitter to avoid herd problem
                        .filter(throwable -> throwable instanceof RateLimitException))
                .onErrorResume(ex -> {
                    log.warn("Fallback triggered due to error: {}", ex.toString());
                    return Mono.empty();
                })
                .block();
    }

    private Mono<? extends Throwable> handleErrors(ClientResponse clientResponse, String endpointUrl) {
        Mono<JsonNode> errMessage = clientResponse.bodyToMono(JsonNode.class);
        return errMessage.flatMap(msg -> {
            if (clientResponse.statusCode().value() == 404) {
                return Mono.empty();
            } else if (clientResponse.statusCode().value() == 500
                    && msg.get("error").has("message")) {
                throw new BaseException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        msg.get("error").get("message").asText());
            } else if (clientResponse.statusCode().value() == 400) {
                throw new BaseException(
                        HttpStatus.INTERNAL_SERVER_ERROR, msg.get("message").asText());
            }
            log.error("Error from service call with URL : " + endpointUrl + " with status code: "
                    + clientResponse.statusCode().value() + " message: " + msg);
            throw new BaseException(
                    HttpStatus.INTERNAL_SERVER_ERROR, msg.get("status").asText());
        });
    }
}
