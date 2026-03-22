package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.ProviderAuthMethod;
import opencode.sdk.model.ProviderList200Response;
import opencode.sdk.model.ProviderList200ResponseAllInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ProviderExample {

    private static final Logger logger = LoggerFactory.getLogger(ProviderExample.class);

    private final OpenCodeClient client;

    public ProviderExample(OpenCodeClient client) {
        this.client = client;
    }

    public void demonstrateProviders() {
        try {
            logger.info("=== Provider Example ===");

            // List AI providers
            listProviders();

            // Get auth methods
            getAuthMethods();

            logger.info("=== Provider Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during provider operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during provider operations: {}", e.getMessage(), e);
        }
    }

    private void listProviders() throws ApiException {
        logger.info("\n--- Listing AI Providers ---");

        ProviderList200Response response = client.api().providerList(
                null,  // directory
                null   // workspace
        );

        List<ProviderList200ResponseAllInner> allProviders = response.getAll();
        List<String> connectedProviders = response.getConnected();
        Map<String, String> defaults = response.getDefault();

        logger.info("Found {} providers:", allProviders.size());
        for (ProviderList200ResponseAllInner provider : allProviders) {
            logger.info("  - ID: {}", provider.getId());
            logger.info("    Name: {}", provider.getName());
            if (provider.getApi() != null) {
                logger.info("    API: {}", provider.getApi());
            }
            if (provider.getNpm() != null) {
                logger.info("    NPM: {}", provider.getNpm());
            }
            logger.info("    Environment Variables: {}", provider.getEnv());
            logger.info("    Models: {} model(s)", provider.getModels().size());
        }

        if (!connectedProviders.isEmpty()) {
            logger.info("Connected providers: {}", connectedProviders);
        }

        if (!defaults.isEmpty()) {
            logger.info("Default provider mappings:");
            for (Map.Entry<String, String> entry : defaults.entrySet()) {
                logger.info("  {} -> {}", entry.getKey(), entry.getValue());
            }
        }
    }

    private void getAuthMethods() throws ApiException {
        logger.info("\n--- Getting Provider Auth Methods ---");

        Map<String, List<ProviderAuthMethod>> authMethods = client.api().providerAuth(
                null,  // directory
                null   // workspace
        );

        if (authMethods.isEmpty()) {
            logger.info("No auth methods configured.");
            return;
        }

        logger.info("Found auth methods for {} provider(s):", authMethods.size());
        for (Map.Entry<String, List<ProviderAuthMethod>> entry : authMethods.entrySet()) {
            String providerId = entry.getKey();
            List<ProviderAuthMethod> methods = entry.getValue();

            logger.info("  Provider: {}", providerId);
            for (ProviderAuthMethod method : methods) {
                logger.info("    - Type: {}", method.getType());
                logger.info("      Label: {}", method.getLabel());
            }
        }
    }

    public static void main(String[] args) {
        logger.info("Starting Provider Example");
        logger.info("========================");

        // Configure the client with Basic Auth
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("http://localhost:4096");
        config.setUsername("opencode");
        config.setPassword("opencode123");

        // Create the client
        OpenCodeClient client = new OpenCodeClient(config);

        try {
            // Run the example
            ProviderExample example = new ProviderExample(client);
            example.demonstrateProviders();

            logger.info("\n");
            logger.info("========================");
            logger.info("Example completed successfully!");

        } catch (Exception e) {
            logger.error("Error running example: {}", e.getMessage(), e);
            System.err.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
