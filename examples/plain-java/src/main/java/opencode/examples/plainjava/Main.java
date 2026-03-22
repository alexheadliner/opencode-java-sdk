package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.model.ApiResponse;

public class Main {

    public static void main(String[] args) {
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("https://api.opencode.dev");
        config.setApiKey("your-api-key");

        OpenCodeClient client = new OpenCodeClient(config);
        ApiResponse response = client.get("/v1/resources");

        System.out.println("Status: " + response.getStatus());
        System.out.println("Data: " + response.getData());
        System.out.println("Message: " + response.getMessage());
    }
}
