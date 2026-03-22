package opencode.sdk.client;

import java.net.http.HttpClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.model.ApiResponse;

public class OpenCodeClient {

    private OpenCodeConfig config;
    private HttpClient httpClient;

    public OpenCodeClient(OpenCodeConfig config) {
        this.config = config;
        this.httpClient = HttpClient.newHttpClient();
    }

    public ApiResponse get(String endpoint) {
        return new ApiResponse();
    }
}
