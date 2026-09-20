package com.resilience4j.demo.retry;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.util.function.Predicate;

public class ApiRetryExceptionPredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {

        // Network/connect/read timeout type failures
        if (throwable instanceof ResourceAccessException) {
            return true;
        }

        // HTTP response errors
        if (throwable instanceof HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();

            // Retry HTTP 429
            if (statusCode == 429) {
                return true;
            }

            // Retry all 5xx
            return exception.getStatusCode().is5xxServerError();
        }

        // Don't retry anything else
        return false;
    }
}