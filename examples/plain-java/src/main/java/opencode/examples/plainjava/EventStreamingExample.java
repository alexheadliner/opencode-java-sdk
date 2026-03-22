package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.Event;
import opencode.sdk.model.GlobalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventStreamingExample {

    private static final Logger logger = LoggerFactory.getLogger(EventStreamingExample.class);

    private final OpenCodeClient client;

    public EventStreamingExample(OpenCodeClient client) {
        this.client = client;
    }

    public void demonstrateEventStreaming() {
        try {
            logger.info("=== Event Streaming Example ===");

            // Demonstrate project event subscription
            demonstrateProjectEventSubscribe();

            // Demonstrate global event subscription
            demonstrateGlobalEvent();

            logger.info("=== Event Streaming Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during event streaming operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during event streaming operations: {}", e.getMessage(), e);
        }
    }

    private void demonstrateProjectEventSubscribe() throws ApiException {
        logger.info("\n--- Subscribing to Project Events (SSE) ---");

        Event event = client.api().eventSubscribe(
                null,  // directory - uses current directory
                null   // workspace - uses default workspace
        );

        logger.info("Successfully subscribed to project events!");
        logEventDetails(event);
    }

    private void demonstrateGlobalEvent() throws ApiException {
        logger.info("\n--- Subscribing to Global Events (SSE) ---");

        GlobalEvent globalEvent = client.api().globalEvent();

        logger.info("Successfully subscribed to global events!");
        logger.info("  Directory: {}", globalEvent.getDirectory());

        if (globalEvent.getPayload() != null) {
            logger.info("  Event Payload:");
            logEventDetails(globalEvent.getPayload());
        }
    }

    private void logEventDetails(Event event) {
        if (event == null) {
            logger.info("    Event: null");
            return;
        }

        logger.info("    Event Type: {}", event.getClass().getSimpleName());
        logger.info("    Event toString: {}", event.toString());
    }

    public static void main(String[] args) {
        logger.info("Starting Event Streaming Example");
        logger.info("=================================");

        // Configure the client with Basic Auth
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("http://localhost:4096");
        config.setUsername("opencode");
        config.setPassword("opencode123");

        // Create the client
        OpenCodeClient client = new OpenCodeClient(config);

        try {
            // Run the example
            EventStreamingExample example = new EventStreamingExample(client);
            example.demonstrateEventStreaming();

            logger.info("\n");
            logger.info("=================================");
            logger.info("Example completed successfully!");

        } catch (Exception e) {
            logger.error("Error running example: {}", e.getMessage(), e);
            System.err.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
