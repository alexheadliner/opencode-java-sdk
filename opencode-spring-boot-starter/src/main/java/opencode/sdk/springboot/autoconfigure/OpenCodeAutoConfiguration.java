package opencode.sdk.springboot.autoconfigure;

import lombok.RequiredArgsConstructor;
import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.springboot.OpenCodeService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(OpenCodeProperties.class)
@RequiredArgsConstructor
public class OpenCodeAutoConfiguration {

    private final OpenCodeProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public OpenCodeConfig openCodeConfig() {
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl(properties.getBaseUrl());
        config.setApiKey(properties.getApiKey());
        config.setTimeout(properties.getTimeout());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenCodeClient openCodeClient(OpenCodeConfig config) {
        return new OpenCodeClient(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenCodeService openCodeService(OpenCodeClient openCodeClient) {
        return new OpenCodeService(openCodeClient);
    }
}
