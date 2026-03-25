package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.FileNode;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileOperationsControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetFileTree() {
        ResponseEntity<List<FileNode>> response =
                restTemplate.exchange("/api/files/tree?path=.", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldSearchFiles() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/files/search?pattern=test", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldFindFiles() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/files/find?query=*.java", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldGetFileStatus() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/api/files/status", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }
}
