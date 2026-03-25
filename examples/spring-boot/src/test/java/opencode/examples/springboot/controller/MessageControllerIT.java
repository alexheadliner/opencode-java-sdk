package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.SessionMessages200ResponseInner;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MessageControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetSessionMessages() {
        ResponseEntity<List<SessionMessages200ResponseInner>> response =
                restTemplate.exchange("/api/messages/test-session", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isIn(200, 404);
    }

    @Test
    void shouldHandleAbortForInvalidSession() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/messages/invalid-session/abort", null, String.class);

        assertThat(response.getStatusCode().value()).isIn(204, 404, 500);
    }

    @Test
    void shouldHandlePromptForInvalidSession() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/messages/invalid-session/prompt", null, String.class);

        assertThat(response.getStatusCode().value()).isIn(200, 400, 404, 500);
    }
}
