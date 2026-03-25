package opencode.examples.springboot.controller;

import opencode.examples.springboot.testsupport.AbstractIntegrationTest;
import opencode.sdk.model.VcsInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class VcsControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldGetVcsInfo() {
        ResponseEntity<VcsInfo> response =
                restTemplate.getForEntity("/api/vcs", VcsInfo.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }
}
