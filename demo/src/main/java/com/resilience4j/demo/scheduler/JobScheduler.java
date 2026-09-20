package com.resilience4j.demo.scheduler;

import com.resilience4j.demo.client.ApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final ApiClient apiClient;

    @Scheduled(
            fixedDelay = 5 * 60 * 1000,
            initialDelay = 5 * 1000
    )
    public void apiCaller() {
        apiClient.restApiCaller();
    }
}
