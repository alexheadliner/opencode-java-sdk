package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.Config;
import opencode.sdk.model.ConfigProviders200Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetProjectConfiguration() {
        ResponseEntity<Config> response =
                restTemplate.getForEntity("/api/config/project", Config.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldGetGlobalConfiguration() {
        ResponseEntity<Config> response =
                restTemplate.getForEntity("/api/config/global", Config.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldUpdateConfiguration() {
        Config config = new Config();
        config.setModel("test-model");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Config> entity = new HttpEntity<>(config, headers);

        ResponseEntity<Config> response =
                restTemplate.exchange("/api/config/project", HttpMethod.PATCH, entity, Config.class);

        assertThat(response.getStatusCode().value()).isIn(200, 204);
    }

    @Test
    void shouldGetProviders() {
        ResponseEntity<ConfigProviders200Response> response =
                restTemplate.getForEntity("/api/config/providers", ConfigProviders200Response.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }
}
