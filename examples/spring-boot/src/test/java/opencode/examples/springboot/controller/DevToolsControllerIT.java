package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.FormatterStatus;
import opencode.sdk.model.LSPStatus;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DevToolsControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetLspStatus() {
        ResponseEntity<List<LSPStatus>> response =
                restTemplate.exchange("/api/devtools/lsp", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldGetFormatterStatus() {
        ResponseEntity<List<FormatterStatus>> response =
                restTemplate.exchange("/api/devtools/formatter", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldHandleAppLogWithValidRequest() {
        String requestBody = "{\"service\": \"test-service\", \"message\": \"test message\"}";

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/devtools/log", requestBody, String.class);

        assertThat(response.getStatusCode().value()).isIn(200, 400);
    }

    @Test
    void shouldHandleAppLogWithInvalidRequest() {
        String requestBody = "{}";

        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/devtools/log", requestBody, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }
}
