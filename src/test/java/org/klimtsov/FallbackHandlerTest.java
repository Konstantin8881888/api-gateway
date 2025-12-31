package org.klimtsov;

import org.junit.jupiter.api.Test;
import org.klimtsov.fallback.FallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@WebFluxTest
@Import(FallbackHandler.class)
class FallbackHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void userServiceFallback_ReturnsCorrectResponse() {
        webTestClient.get().uri("/fallback/user-service")
                .exchange()
                .expectStatus().isEqualTo(SERVICE_UNAVAILABLE)
                .expectBody()
                .jsonPath("$.message").isEqualTo("User Service is temporarily unavailable. Please try again later.")
                .jsonPath("$.service").isEqualTo("user-service")
                .jsonPath("$.status").isEqualTo(503)
                .jsonPath("$.timestamp").exists();
    }

    @Test
    void notificationServiceFallback_ReturnsCorrectResponse() {
        webTestClient.get().uri("/fallback/notification-service")
                .exchange()
                .expectStatus().isEqualTo(SERVICE_UNAVAILABLE)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Notification Service is temporarily unavailable. Please try again later.")
                .jsonPath("$.service").isEqualTo("notification-service")
                .jsonPath("$.status").isEqualTo(503);
    }
}