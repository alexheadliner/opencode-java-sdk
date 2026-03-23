package opencode.sdk.springboot.autoconfigure;

import opencode.sdk.api.DefaultApi;
import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@ConditionalOnClass(OpenCodeClient.class)
@EnableConfigurationProperties(OpenCodeProperties.class)
public class OpenCodeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpenCodeConfig openCodeConfig(OpenCodeProperties properties) {
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl(properties.getBaseUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiClient apiClient(OpenCodeConfig config) {
        ApiClient client = new ApiClient();

        if (config.getBaseUrl() != null) {
            client.updateBaseUri(config.getBaseUrl());
        }

        if (config.getUsername() != null && config.getPassword() != null) {
            String authHeader = createBasicAuthHeader(config.getUsername(), config.getPassword());
            client.setRequestInterceptor(builder -> builder.header("Authorization", authHeader));
        }

        return client;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultApi defaultApi(ApiClient apiClient) {
        return new DefaultApi(apiClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenCodeClient openCodeClient(OpenCodeConfig config) {
        return new OpenCodeClient(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public opencode.sdk.springboot.OpenCodeService openCodeService(DefaultApi defaultApi, ApiClient apiClient) {
        return new opencode.sdk.springboot.OpenCodeService(defaultApi, apiClient);
    }

    private String createBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encoded;
    }
}
