package com.resilience4j.demo.client;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {

    private int attempt = 1;

    @Retry(name = "api-client", fallbackMethod = "fallback")
    public void restApiCaller() {
        log.info("Call {}", attempt++);
        RestClient.builder().build().get()
                .uri("http://localhost:8081/get-call/400")
                .retrieve()
                .body(String.class);
    }

    public void fallback(Exception e) {
        log.info("Fallback invoked");
        log.error("Error {}", e.getMessage());
    }

}
