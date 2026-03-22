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

            // Run System Info Example
            logger.info("\n");
            logger.info("========================================");
            SystemInfoExample systemInfoExample = new SystemInfoExample(client);
            systemInfoExample.demonstrateSystemInfo();

            // Run Configuration Example
            logger.info("\n");
            logger.info("========================================");
            ConfigurationExample configurationExample = new ConfigurationExample(client);
            configurationExample.demonstrateConfiguration();

            // Run Provider Example
            logger.info("\n");
            logger.info("========================================");
            ProviderExample providerExample = new ProviderExample(client);
            providerExample.demonstrateProviders();

            // Run Project Example
            logger.info("\n");
            logger.info("========================================");
            ProjectExample projectExample = new ProjectExample(client);
            projectExample.demonstrateProjectOperations();

            // Run File Operations Example
            logger.info("\n");
            logger.info("========================================");
            FileOperationsExample fileOperationsExample = new FileOperationsExample(client);
            fileOperationsExample.demonstrateFileOperations();

            // Run Session CRUD Example
            logger.info("\n");
            logger.info("========================================");
            SessionCrudExample sessionCrudExample = new SessionCrudExample(client);
            sessionCrudExample.demonstrateSessionCrud();

            // Run Session Advanced Example
            logger.info("\n");
            logger.info("========================================");
            SessionAdvancedExample sessionAdvancedExample = new SessionAdvancedExample(client);
            sessionAdvancedExample.demonstrateAdvancedSessionOperations();

            // Run Message Example
            logger.info("\n");
            logger.info("========================================");
            MessageExample messageExample = new MessageExample(client);
            messageExample.demonstrateMessaging();

            // ========== Phase 2 Examples ==========

            // Run DevTools Example
            logger.info("\n");
            logger.info("========================================");
            DevToolsExample devToolsExample = new DevToolsExample(client);
            devToolsExample.demonstrateDevTools();

            // Run Experimental Example
            logger.info("\n");
            logger.info("========================================");
            ExperimentalExample experimentalExample = new ExperimentalExample(client);
            experimentalExample.demonstrateExperimentalApis();

            // Run Instance Example
            logger.info("\n");
            logger.info("========================================");
            InstanceExample instanceExample = new InstanceExample(client);
            instanceExample.demonstrateInstanceManagement();

            // Run Interactive Example
            logger.info("\n");
            logger.info("========================================");
            InteractiveExample interactiveExample = new InteractiveExample(client);
            interactiveExample.demonstrateInteractiveApis();

            // Run MCP Example
            logger.info("\n");
            logger.info("========================================");
            McpExample mcpExample = new McpExample(client);
            mcpExample.demonstrateMcpOperations();

            // Run Todo Example
            logger.info("\n");
            logger.info("========================================");
            TodoExample todoExample = new TodoExample(client);
            todoExample.demonstrateTodoOperations();

            // Run VCS Example
            logger.info("\n");
            logger.info("========================================");
            VcsExample vcsExample = new VcsExample(client);
            vcsExample.demonstrateVcsOperations();

            // Run Event Streaming Example
            logger.info("\n");
            logger.info("========================================");
            EventStreamingExample eventStreamingExample = new EventStreamingExample(client);
            eventStreamingExample.demonstrateEventStreaming();

            // Run PTY Example
            logger.info("\n");
            logger.info("========================================");
            PtyExample ptyExample = new PtyExample(client);
            ptyExample.demonstratePtyOperations();

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
