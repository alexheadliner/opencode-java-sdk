package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.SessionForkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class SessionAdvancedControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldReturnNotFoundForInvalidSessionFork() {
        SessionForkRequest request = new SessionForkRequest();
        request.setMessageID("msg_123");

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/sessions/advanced/invalid-id/fork", request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldReturnNotFoundForInvalidSessionShare() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/sessions/advanced/invalid-id/share", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldReturnNotFoundForInvalidSessionChildren() {
        ResponseEntity<String> response =
                restTemplate.exchange("/api/sessions/advanced/invalid-id/children", HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldReturnNotFoundForInvalidSessionCommand() {
        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/sessions/advanced/invalid-id/command", null, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }
}
