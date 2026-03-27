package opencode.examples.plainjava;

import opencode.examples.plainjava.testing.ExampleContext;
import opencode.examples.plainjava.testing.ResponseValidator;
import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.ExperimentalWorkspaceCreateRequest;
import opencode.sdk.model.GlobalSession;
import opencode.sdk.model.McpResource;
import opencode.sdk.model.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ExperimentalExample {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentalExample.class);

    private final OpenCodeClient client;
    private final ResponseValidator validator;

    public ExperimentalExample(OpenCodeClient client) {
        this.client = client;
        this.validator = null;
    }

    public ExperimentalExample(ExampleContext context) {
        this.client = context.getClient();
        this.validator = context.getValidator();
    }

    public void demonstrateExperimentalApis() {
        try {
            logger.info("=== Experimental APIs Example ===");

            // List all sessions globally
            listGlobalSessions();

            // List workspaces
            listWorkspaces();

            // Create a workspace
            Workspace createdWorkspace = createWorkspace("example-workspace");

            // List workspaces again to show the new one
            listWorkspaces();

            // List MCP resources
            listMcpResources();

            logger.info("=== Experimental APIs Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during experimental operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during experimental operations: {}", e.getMessage(), e);
        }
    }

    private void listGlobalSessions() throws ApiException {
        logger.info("\n--- Listing Global Sessions ---");

        List<GlobalSession> sessions = client.api().experimentalSessionList(
                null,  // directory
                null,  // workspace
                null,  // roots
                null,  // start
                null,  // cursor
                null,  // search
                new BigDecimal("10"),  // limit
                null   // archived
        );

        if (validator != null) {
            validator.validateCollection(sessions, "global sessions");
        }

        logger.info("Found {} global sessions:", sessions.size());
        for (GlobalSession session : sessions) {
            if (validator != null) {
                validator.validateNonNull(session.getId(), "session id");
            }

            logger.info("  - ID: {}, Title: {}",
                    session.getId(),
                    session.getTitle());
        }
    }

    private void listWorkspaces() throws ApiException {
        logger.info("\n--- Listing Workspaces ---");

        List<Workspace> workspaces = client.api().experimentalWorkspaceList(
                null,  // directory
                null   // workspace
        );

        if (validator != null) {
            validator.validateCollection(workspaces, "workspaces");
        }

        logger.info("Found {} workspaces:", workspaces.size());
        for (Workspace workspace : workspaces) {
            if (validator != null) {
                validator.validateNonNull(workspace.getId(), "workspace id");
            }

            logger.info("  - ID: {}, Name: {}",
                    workspace.getId(),
                    workspace.getName());
        }
    }

    private Workspace createWorkspace(String name) throws ApiException {
        logger.info("\n--- Creating Workspace: {} ---", name);

        ExperimentalWorkspaceCreateRequest request = new ExperimentalWorkspaceCreateRequest();
        request.setType("git");
        request.setBranch("main");

        Workspace workspace = client.api().experimentalWorkspaceCreate(
                null,  // directory
                null,  // workspace
                request
        );

        logger.info("Workspace created successfully:");
        logger.info("  ID: {}", workspace.getId());
        logger.info("  Name: {}", workspace.getName());
        logger.info("  Type: {}", workspace.getType());

        return workspace;
    }

    private void listMcpResources() throws ApiException {
        logger.info("\n--- Listing MCP Resources ---");

        Map<String, McpResource> resources = client.api().experimentalResourceList(
                null,  // directory
                null   // workspace
        );

        logger.info("Found {} MCP resources:", resources.size());
        for (Map.Entry<String, McpResource> entry : resources.entrySet()) {
            McpResource resource = entry.getValue();
            logger.info("  - Key: {}, Name: {}, URI: {}",
                    entry.getKey(),
                    resource.getName(),
                    resource.getUri());
        }
    }

    public static void main(String[] args) {
        try {
            // Create client configuration
            opencode.sdk.config.OpenCodeConfig config = new opencode.sdk.config.OpenCodeConfig();
            config.setBaseUrl("http://localhost:4096");
            config.setUsername("opencode");
            config.setPassword("opencode123");

            // Create the client
            OpenCodeClient client = new OpenCodeClient(config);

            // Run the example
            ExperimentalExample example = new ExperimentalExample(client);
            example.demonstrateExperimentalApis();

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
