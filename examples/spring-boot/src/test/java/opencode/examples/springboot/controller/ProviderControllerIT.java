package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.ProviderList200Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldListProviders() {
        ResponseEntity<ProviderList200Response> response =
                restTemplate.getForEntity("/api/providers", ProviderList200Response.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldGetProvider() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/providers/test-provider", String.class);

        assertThat(response.getStatusCode().value()).isIn(200, 404);
    }

    @Test
    void shouldHandleOAuthAuthorize() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/providers/test/oauth/authorize", null, String.class);

        assertThat(response.getStatusCode().value()).isIn(200, 400, 404);
    }
}
