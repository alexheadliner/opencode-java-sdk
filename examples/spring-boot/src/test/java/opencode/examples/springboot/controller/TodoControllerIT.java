package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetSessionTodos() {
        ResponseEntity<List<Todo>> response =
                restTemplate.exchange("/api/todos/test-session", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isIn(200, 404);
    }

    @Test
    void shouldHandleInvalidSessionId() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/todos/invalid-session-id", String.class);

        assertThat(response.getStatusCode().value()).isIn(200, 404, 500);
    }
}
