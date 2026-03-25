package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.AppSkills200ResponseInner;
import opencode.sdk.model.GlobalHealth200Response;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SystemInfoControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldReturnHealthStatus() {
        ResponseEntity<GlobalHealth200Response> response =
                restTemplate.getForEntity("/api/system/health", GlobalHealth200Response.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldReturnSkills() {
        ResponseEntity<List<AppSkills200ResponseInner>> response =
                restTemplate.exchange("/api/system/skills", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }
}
