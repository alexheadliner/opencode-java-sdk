package opencode.examples.plainjava;

import opencode.sdk.client.OpenCodeClient;
import opencode.sdk.config.OpenCodeConfig;
import opencode.sdk.invoker.ApiException;
import opencode.sdk.model.Project;
import opencode.sdk.model.ProjectUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProjectExample {

    private static final Logger logger = LoggerFactory.getLogger(ProjectExample.class);

    private final OpenCodeClient client;

    public ProjectExample(OpenCodeClient client) {
        this.client = client;
    }

    public void demonstrateProjectOperations() {
        try {
            logger.info("=== Project Example ===");

            // List all projects
            listProjects();

            // Get current project
            Project currentProject = getCurrentProject();

            // Update project if current project exists
            if (currentProject != null) {
                updateProject(currentProject.getId());
            }

            logger.info("=== Project Example Completed Successfully ===");

        } catch (ApiException e) {
            logger.error("API Error during project operations: {} - {}", e.getCode(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error during project operations: {}", e.getMessage(), e);
        }
    }

    private void listProjects() throws ApiException {
        logger.info("\n--- Listing All Projects ---");

        List<Project> projects = client.api().projectList(
            null,  // directory
            null   // workspace
        );

        logger.info("Found {} project(s):", projects.size());
        for (Project project : projects) {
            logger.info("  Project ID: {}", project.getId());
            logger.info("    Worktree: {}", project.getWorktree());
            if (project.getName() != null) {
                logger.info("    Name: {}", project.getName());
            }
            if (project.getVcs() != null) {
                logger.info("    VCS: {}", project.getVcs());
            }
            if (project.getTime() != null && project.getTime().getCreated() != null) {
                logger.info("    Created: {}", project.getTime().getCreated());
            }
            if (!project.getSandboxes().isEmpty()) {
                logger.info("    Sandboxes: {}", project.getSandboxes());
            }
        }
    }

    private Project getCurrentProject() throws ApiException {
        logger.info("\n--- Retrieving Current Project ---");

        Project project = client.api().projectCurrent(
            null,  // directory
            null   // workspace
        );

        logger.info("Current project retrieved successfully!");
        logger.info("  Project ID: {}", project.getId());
        logger.info("  Worktree: {}", project.getWorktree());
        if (project.getName() != null) {
            logger.info("  Name: {}", project.getName());
        }
        if (project.getVcs() != null) {
            logger.info("  VCS: {}", project.getVcs());
        }
        if (project.getTime() != null && project.getTime().getCreated() != null) {
            logger.info("  Created: {}", project.getTime().getCreated());
        }
        if (!project.getSandboxes().isEmpty()) {
            logger.info("  Sandboxes: {}", project.getSandboxes());
        }

        return project;
    }

    private void updateProject(String projectId) throws ApiException {
        logger.info("\n--- Updating Project ---");

        // Create update request with only the fields we want to modify
        ProjectUpdateRequest updateRequest = new ProjectUpdateRequest();
        updateRequest.setName("Updated Project Name");

        Project updatedProject = client.api().projectUpdate(
            projectId,      // projectID (required)
            null,           // directory
            null,           // workspace
            updateRequest   // update request
        );

        logger.info("Project updated successfully!");
        logger.info("  Project ID: {}", updatedProject.getId());
        if (updatedProject.getName() != null) {
            logger.info("  New Name: {}", updatedProject.getName());
        }
    }

    public static void main(String[] args) {
        logger.info("Starting Project Example");
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
            ProjectExample example = new ProjectExample(client);
            example.demonstrateProjectOperations();

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
