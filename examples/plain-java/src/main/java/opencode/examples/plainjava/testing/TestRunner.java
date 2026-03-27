package opencode.examples.plainjava.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    public static void main(String[] args) {
        try {
            // Parse command-line arguments
            ArgumentParser parser = new ArgumentParser();
            TestConfiguration config = parser.parse(args);

            if (config == null) {
                // Invalid arguments, usage already printed
                System.exit(1);
            }

            // Load environment variables as defaults
            EnvironmentLoader loader = new EnvironmentLoader();
            loader.loadIntoConfiguration(config);

            // Mask sensitive data in logs
            logger.info("Starting test execution with configuration: {}",
                    SensitiveDataMasker.maskAllSensitiveData(config.toString(), config));

            // Create executor
            TestExecutor executor = new TestExecutor(config);

            // Execute tests
            TestResults results = executor.executeAll();

            // Report results
            executor.getReporter().reportSummary(results);

            // Flush logs
            executor.getTestLogger().flush();

            // Exit with appropriate status code
            int exitCode = results.getFailedCount() > 0 ? 1 : 0;
            System.exit(exitCode);

        } catch (Exception e) {
            logger.error("Fatal error during test execution: {}", e.getMessage(), e);
            System.err.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
