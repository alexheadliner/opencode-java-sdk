package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiException;

public class Main {

    public static void main(String[] args) {
        // Configure the client with Basic Auth
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("http://localhost:4096");
        config.setUsername("opencode");
        config.setPassword("opencode123");

        // Create the client
        OpenCodeClient client = new OpenCodeClient(config);

        try {
            // Example: Get health information
            var health = client.api().globalHealth();
            System.out.println("Health check successful!");
            System.out.println("Healthy: " + health.getHealthy());
            System.out.println("Version: " + health.getVersion());
        } catch (ApiException e) {
            System.err.println("API Error: " + e.getMessage());
            System.err.println("Status Code: " + e.getCode());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
