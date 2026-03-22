package opencode.sdk.client;

import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.api.DefaultApi;
import opencode.sdk.invoker.ApiClient;

public class OpenCodeClient {

    private final ApiClient apiClient;
    private final DefaultApi defaultApi;

    public OpenCodeClient(OpenCodeConfig config) {
        this.apiClient = createApiClient(config);
        this.defaultApi = new DefaultApi(this.apiClient);
    }

    private ApiClient createApiClient(OpenCodeConfig config) {
        ApiClient client = new ApiClient();

        if (config.getBaseUrl() != null) {
            client.updateBaseUri(config.getBaseUrl());
        }

        if (config.getUsername() != null && config.getPassword() != null) {
            // Set up HTTP Basic Authentication using request interceptor
            String auth = config.getUsername() + ":" + config.getPassword();
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            client.setRequestInterceptor(builder -> 
                builder.header("Authorization", "Basic " + encodedAuth)
            );
        }

        return client;
    }

    public DefaultApi api() {
        return defaultApi;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}
