package opencode.sdk.springboot.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "opencode")
public class OpenCodeProperties {

    private String baseUrl = "http://localhost:8080";
    private String apiKey;
    private int timeout = 30;
}
