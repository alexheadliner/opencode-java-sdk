package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting OpenCode Java SDK Examples");
        logger.info("====================================");

        // Configure the client with Basic Auth
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("http://localhost:4096");
        config.setUsername("opencode");
        config.setPassword("opencode123");

        // Create the client
        OpenCodeClient client = new OpenCodeClient(config);

        try {
            // First, verify connection with health check
            performHealthCheck(client);

            // Run Session CRUD Example
            logger.info("\n");
            logger.info("========================================");
            SessionCrudExample sessionCrudExample = new SessionCrudExample(client);
            sessionCrudExample.demonstrateSessionCrud();

            // Run Message Example
            logger.info("\n");
            logger.info("========================================");
            MessageExample messageExample = new MessageExample(client);
            messageExample.demonstrateMessaging();

            logger.info("\n");
            logger.info("====================================");
            logger.info("All examples completed successfully!");

        } catch (Exception e) {
            logger.error("Error running examples: {}", e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void performHealthCheck(OpenCodeClient client) {
        logger.info("\n--- Health Check ---");

        try {
            var health = client.api().globalHealth();
            logger.info("Health check successful!");
            logger.info("  Healthy: {}", health.getHealthy());
            logger.info("  Version: {}", health.getVersion());
        } catch (ApiException e) {
            logger.error("Health check failed: {} - {}", e.getCode(), e.getMessage());
            throw new RuntimeException("Cannot connect to OpenCode server. Please ensure the server is running at http://localhost:4096", e);
        }
    }
}
