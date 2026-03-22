package opencode.examples.plainjava;

import java.util.List;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.api.SessionApi;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiClient;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.Session;
import opencode.sdk.model.SessionCreateRequest;
import opencode.sdk.model.SessionForkRequest;
import opencode.sdk.model.SessionRevertRequest;
import opencode.sdk.model.SessionSummarizeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionAdvancedExample {

    private static final Logger logger = LoggerFactory.getLogger(SessionAdvancedExample.class);

    private final OpenCodeClient client;
    private final SessionApi sessionApi;

    public SessionAdvancedExample(OpenCodeClient client) {
        this.client = client;
        ApiClient apiClient = client.getApiClient();
        this.sessionApi = new SessionApi(apiClient);
    }

    public void demonstrateAdvancedSessionOperations() {
        try {
            logger.info("=== Session Advanced Operations Example ===");

            // Create a session for demonstrating advanced operations
            String sessionId = createSession("Advanced Operations Demo Session");
            logger.info("Created session with ID: {}", sessionId);

            // Demonstrate getting session details via SessionApi
            getSessionDetails(sessionId);

            // Demonstrate session forking
            forkSession(sessionId);

            // Demonstrate getting session children via SessionApi
            getSessionChildren(sessionId);

            // Demonstrate session sharing
            shareSession(sessionId);

            // Demonstrate session unsharing
            unshareSession(sessionId);

            // Demonstrate session summarization
            summarizeSession(sessionId);

            // Demonstrate session abort
            abortSession(sessionId);

            // Demonstrate session revert
            revertSession(sessionId);

            // Demonstrate session unrevert
            unrevertSession(sessionId);

            logger.info("=== Session Advanced Operations Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during advanced session operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during advanced session operations: {}", e.getMessage(), e);
        }
    }

    private String createSession(String title) throws ApiException {
        logger.info("\n--- Creating Session ---");

        SessionCreateRequest request = new SessionCreateRequest();
        request.setTitle(title);

        Session session = client.api().sessionCreate(
            null,
            null,
            request
        );

        logger.info("Session created successfully");
        return session.getId();
    }

    private void forkSession(String sessionId) throws ApiException {
        logger.info("\n--- Forking Session: {} ---", sessionId);

        SessionForkRequest request = new SessionForkRequest();
        request.setMessageID(null);

        Session forkedSession = client.api().sessionFork(
            sessionId,
            null,
            null,
            request
        );

        logger.info("Session forked successfully. New session ID: {}", forkedSession.getId());
    }

    private void getSessionDetails(String sessionId) throws ApiException {
        logger.info("\n--- Getting Session Details via SessionApi: {} ---", sessionId);

        Session session = sessionApi.sessionGet(sessionId, null, null);

        logger.info("Session retrieved successfully");
        logger.info("  ID: {}", session.getId());
        logger.info("  Title: {}", session.getTitle());
        logger.info("  Directory: {}", session.getDirectory());
        if (session.getTime() != null) {
            logger.info("  Time: created={}, updated={}",
                session.getTime().getCreated(),
                session.getTime().getUpdated());
        }
        if (session.getSummary() != null) {
            logger.info("  Summary: additions={}, deletions={}, files={}",
                session.getSummary().getAdditions(),
                session.getSummary().getDeletions(),
                session.getSummary().getFiles());
        }
    }

    private void getSessionChildren(String sessionId) throws ApiException {
        logger.info("\n--- Getting Session Children via SessionApi: {} ---", sessionId);

        List<Session> children = sessionApi.sessionChildren(sessionId, null, null);

        if (children != null && !children.isEmpty()) {
            logger.info("Found {} child session(s):", children.size());
            for (Session child : children) {
                logger.info("  - Child ID: {}, Title: {}", child.getId(), child.getTitle());
            }
        } else {
            logger.info("No child sessions found");
        }
    }

    private void shareSession(String sessionId) throws ApiException {
        logger.info("\n--- Sharing Session: {} ---", sessionId);

        Session session = client.api().sessionShare(
            sessionId,
            null,
            null
        );

        if (session.getShare() != null) {
            logger.info("Session shared successfully. Share URL: {}", session.getShare().getUrl());
        } else {
            logger.info("Session shared successfully");
        }
    }

    private void unshareSession(String sessionId) throws ApiException {
        logger.info("\n--- Unsharing Session: {} ---", sessionId);

        Session session = client.api().sessionUnshare(
            sessionId,
            null,
            null
        );

        logger.info("Session unshared successfully");
    }

    private void summarizeSession(String sessionId) throws ApiException {
        logger.info("\n--- Summarizing Session: {} ---", sessionId);

        SessionSummarizeRequest request = new SessionSummarizeRequest();
        request.setProviderID("default");
        request.setModelID("default");
        request.setAuto(true);

        Boolean result = client.api().sessionSummarize(
            sessionId,
            null,
            null,
            request
        );

        if (result) {
            logger.info("Session summarization initiated successfully");
        } else {
            logger.warn("Session summarization returned false");
        }
    }

    private void abortSession(String sessionId) throws ApiException {
        logger.info("\n--- Aborting Session Processing: {} ---", sessionId);

        Boolean result = client.api().sessionAbort(
            sessionId,
            null,
            null
        );

        if (result) {
            logger.info("Session aborted successfully");
        } else {
            logger.warn("Session abort returned false");
        }
    }

    private void revertSession(String sessionId) throws ApiException {
        logger.info("\n--- Reverting Session: {} ---", sessionId);

        SessionRevertRequest request = new SessionRevertRequest();
        request.setMessageID("message-id-to-revert-to");
        request.setPartID(null);

        Session session = client.api().sessionRevert(
            sessionId,
            null,
            null,
            request
        );

        logger.info("Session reverted successfully");
    }

    private void unrevertSession(String sessionId) throws ApiException {
        logger.info("\n--- Unreverting Session: {} ---", sessionId);

        Session session = client.api().sessionUnrevert(
            sessionId,
            null,
            null
        );

        logger.info("Session unreverted successfully");
    }

    public static void main(String[] args) {
        try {
            // Configure the client
            OpenCodeConfig config = new OpenCodeConfig();
            config.setBaseUrl("http://localhost:4096");
            config.setUsername("opencode");
            config.setPassword("opencode123");

            OpenCodeClient client = new OpenCodeClient(config);

            SessionAdvancedExample example = new SessionAdvancedExample(client);
            example.demonstrateAdvancedSessionOperations();

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
