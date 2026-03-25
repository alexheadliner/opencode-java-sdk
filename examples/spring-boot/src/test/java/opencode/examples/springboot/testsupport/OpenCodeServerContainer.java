package opencode.examples.springboot.testsupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class OpenCodeServerContainer extends GenericContainer<OpenCodeServerContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenCodeServerContainer.class);

    private static final int OPENCODE_PORT = 4096;
    private static final String DEFAULT_USERNAME = "opencode";
    private static final String DEFAULT_PASSWORD = "opencode123";
    private static final String IMAGE_NAME = "opencode-server";
    private static final String IMAGE_TAG = "test";

    public OpenCodeServerContainer() {
        super(buildImage());

        LOGGER.info("Initializing OpenCodeServerContainer...");

        withExposedPorts(OPENCODE_PORT);

        // Required environment variables for the OpenCode server
        String apiKey = System.getenv().getOrDefault("Z_AI_API_KEY", "a039df3a972e4c91a2a19096832d7bed.RDyMzFm7qZ4jKXvh");
        LOGGER.info("Using Z_AI_API_KEY: {}...", apiKey.substring(0, Math.min(10, apiKey.length())));

        withEnv("Z_AI_API_KEY", apiKey);
        withEnv("OPENCODE_SERVER_USERNAME", DEFAULT_USERNAME);
        withEnv("OPENCODE_SERVER_PASSWORD", DEFAULT_PASSWORD);
        withEnv("OPENCODE_SERVER_PORT", String.valueOf(OPENCODE_PORT));
        withEnv("OPENCODE_SERVER_HOST", "0.0.0.0");

        // Enable container log output for debugging
        withLogConsumer(new Slf4jLogConsumer(LOGGER));

        // Extended timeout for image build + container startup
        // Image build includes npm install which can take several minutes
        waitingFor(Wait.forHttp("/global/health")
                .forPort(OPENCODE_PORT)
                .forStatusCode(200)
                .withStartupTimeout(Duration.ofMinutes(10)));

        LOGGER.info("OpenCodeServerContainer configured with port {} and 10-minute timeout", OPENCODE_PORT);
    }

    private static ImageFromDockerfile buildImage() {
        // Build from project root - tests run from examples/spring-boot
        // so we need to go up two levels to reach the project root
        Path projectRoot = Paths.get("../..").toAbsolutePath().normalize();
        Path dockerfilePath = projectRoot.resolve("docker/opencode/Dockerfile");

        LOGGER.info("Project root path: {}", projectRoot);
        LOGGER.info("Dockerfile path: {}", dockerfilePath);
        LOGGER.info("Dockerfile exists: {}", dockerfilePath.toFile().exists());
        LOGGER.info("Docker context path: {}", projectRoot.resolve("docker/opencode"));

        if (!dockerfilePath.toFile().exists()) {
            throw new RuntimeException("Dockerfile not found at: " + dockerfilePath);
        }

        // Use ImageFromDockerfile with cache enabled (second parameter = true)
        // This allows Docker to cache layers and speed up subsequent builds
        return new ImageFromDockerfile(IMAGE_NAME + ":" + IMAGE_TAG, true)
                .withDockerfile(dockerfilePath)
                .withFileFromPath(".", projectRoot.resolve("docker/opencode"));
    }

    @Override
    public OpenCodeServerContainer withReuse(boolean reuse) {
        LOGGER.info("Setting container reuse to: {}", reuse);
        super.withReuse(reuse);
        return self();
    }

    public String getBaseUrl() {
        String baseUrl = String.format("http://%s:%d", getHost(), getMappedPort(OPENCODE_PORT));
        LOGGER.info("OpenCode server base URL: {}", baseUrl);
        return baseUrl;
    }

    public boolean isHealthy() {
        boolean running = isRunning();
        LOGGER.info("Container health check - isRunning: {}", running);
        return running;
    }

    @Override
    public void start() {
        LOGGER.info("Starting OpenCodeServerContainer...");
        LOGGER.info("This may take several minutes on first run as the Docker image needs to be built");
        try {
            super.start();
            LOGGER.info("OpenCodeServerContainer started successfully on port: {}", getMappedPort(OPENCODE_PORT));
        } catch (Exception e) {
            LOGGER.error("Failed to start OpenCodeServerContainer: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void stop() {
        LOGGER.info("Stopping OpenCodeServerContainer...");
        super.stop();
        LOGGER.info("OpenCodeServerContainer stopped");
    }
}
