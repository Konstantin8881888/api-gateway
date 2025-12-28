package org.klimtsov.fallback;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FallbackHandler {

    @Bean
    public RouterFunction<ServerResponse> fallbackRouter() {
        return route(GET("/fallback/user-service"), request -> {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User Service is temporarily unavailable. Please try again later.");
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
            response.put("service", "user-service");

            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .bodyValue(response);
        })
                .andRoute(GET("/fallback/notification-service"), request -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Notification Service is temporarily unavailable. Please try again later.");
                    response.put("timestamp", LocalDateTime.now().toString());
                    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
                    response.put("service", "notification-service");

                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .bodyValue(response);
                });
    }
}