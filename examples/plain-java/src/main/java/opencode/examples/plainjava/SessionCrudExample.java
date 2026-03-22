package opencode.examples.plainjava;

import opencode.sdk.api.SessionApi;
import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.invoker.ApiClient;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.Session;
import opencode.sdk.model.SessionCreateRequest;
import opencode.sdk.model.SessionUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class SessionCrudExample {

    private static final Logger logger = LoggerFactory.getLogger(SessionCrudExample.class);

    private final OpenCodeClient client;
    private final SessionApi sessionApi;

    public SessionCrudExample(OpenCodeClient client) {
        this.client = client;
        // Create SessionApi from the client's ApiClient
        ApiClient apiClient = client.getApiClient();
        this.sessionApi = new SessionApi(apiClient);
    }

    public void demonstrateSessionCrud() {
        try {
            logger.info("=== Session CRUD Example ===");

            // List all sessions
            listSessions();

            // Create a new session
            String sessionId = createSession("Java SDK Example Session");
            logger.info("Created session with ID: {}", sessionId);

            // Get session by ID
            getSession(sessionId);

            // Update session title
            updateSession(sessionId, "Updated Java SDK Session Title");

            // Get the updated session
            getSession(sessionId);

            // Delete the session
            deleteSession(sessionId);

            logger.info("=== Session CRUD Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during session operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during session operations: {}", e.getMessage(), e);
        }
    }

    private void listSessions() throws ApiException {
        logger.info("\n--- Listing Sessions ---");

        List<Session> sessions = client.api().sessionList(
                null,  // directory
                null,  // workspace
                null,  // roots - only root sessions
                null,  // start - filter by timestamp
                null,  // search - filter by title
                new BigDecimal("10")  // limit - max 10 sessions
        );

        logger.info("Found {} sessions:", sessions.size());
        for (Session session : sessions) {
            logger.info("  - ID: {}, Title: {}",
                    session.getId(),
                    session.getTitle());
        }
    }

    private String createSession(String title) throws ApiException {
        logger.info("\n--- Creating Session ---");

        SessionCreateRequest request = new SessionCreateRequest();
        request.setTitle(title);

        Session session = client.api().sessionCreate(
                null,  // directory
                null,  // workspace
                request
        );

        logger.info("Session created successfully");
        return session.getId();
    }

    private void getSession(String sessionId) throws ApiException {
        logger.info("\n--- Getting Session by ID: {} ---", sessionId);

        Session session = sessionApi.sessionGet(
                sessionId,
                null,  // directory
                null   // workspace
        );

        logger.info("Session Details:");
        logger.info("  ID: {}", session.getId());
        logger.info("  Title: {}", session.getTitle());
        logger.info("  Slug: {}", session.getSlug());
        logger.info("  Created At: {}", session.getTime().getCreated());
        logger.info("  Updated At: {}", session.getTime().getUpdated());
    }

    private void updateSession(String sessionId, String newTitle) throws ApiException {
        logger.info("\n--- Updating Session: {} ---", sessionId);

        SessionUpdateRequest request = new SessionUpdateRequest();
        request.setTitle(newTitle);

        client.api().sessionUpdate(
                sessionId,
                null,  // directory
                null,  // workspace
                request
        );

        logger.info("Session updated successfully to: {}", newTitle);
    }

    private void deleteSession(String sessionId) throws ApiException {
        logger.info("\n--- Deleting Session: {} ---", sessionId);

        Boolean result = client.api().sessionDelete(
                sessionId,
                null,  // directory
                null   // workspace
        );

        if (result) {
            logger.info("Session deleted successfully");
        } else {
            logger.warn("Session deletion returned false");
        }
    }

    public static void main(String[] args) {
        logger.info("Starting Session CRUD Example");

        // Configure the client with Basic Auth
        opencode.sdk.config.OpenCodeConfig config = new opencode.sdk.config.OpenCodeConfig();
        config.setBaseUrl("http://localhost:4096");
        config.setUsername("opencode");
        config.setPassword("opencode123");

        // Create the client
        OpenCodeClient client = new OpenCodeClient(config);

        try {
            // Verify connection with health check
            var health = client.api().globalHealth();
            logger.info("Connected to OpenCode server (version: {})", health.getVersion());

            // Run the example
            SessionCrudExample example = new SessionCrudExample(client);
            example.demonstrateSessionCrud();

            logger.info("Session CRUD Example completed");

        } catch (ApiException e) {
            logger.error("Failed to connect to OpenCode server: {}", e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}
