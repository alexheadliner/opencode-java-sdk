package opencode.examples.plainjava.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ExampleWrapper {

    private static final Logger logger = LoggerFactory.getLogger(ExampleWrapper.class);

    private final ExampleContext context;
    private final ResponseValidator validator;
    private final ResourceTracker tracker;
    private final ErrorClassifier errorClassifier;

    public ExampleWrapper(ExampleContext context) {
        this.context = context;
        this.validator = context.getValidator();
        this.tracker = context.getResourceTracker();
        this.errorClassifier = new ErrorClassifier();
    }

    public TestResult execute(Object exampleInstance, String exampleName) {
        TestResult result = new TestResult(exampleName);
        result.setProvider(context.getConfig().getProvider());
        result.setModel(context.getConfig().getModel());

        long startTime = System.currentTimeMillis();

        try {
            invokeExampleMethod(exampleInstance);
            result.setSuccess(true);
        } catch (Exception e) {
            handleException(e, result);
        } finally {
            long endTime = System.currentTimeMillis();
            result.setExecutionTimeMs(endTime - startTime);
        }

        return result;
    }

    private void invokeExampleMethod(Object instance) throws Exception {
        // Find the demonstrate method (e.g., demonstrateSystemInfo, demonstrateConfiguration, etc.)
        Method[] methods = instance.getClass().getDeclaredMethods();
        Method demonstrateMethod = null;

        for (Method method : methods) {
            if (method.getName().startsWith("demonstrate") && method.getParameterCount() == 0) {
                demonstrateMethod = method;
                break;
            }
        }

        if (demonstrateMethod == null) {
            throw new RuntimeException("No demonstrate method found in example class");
        }

        // Make method accessible if it's private
        demonstrateMethod.setAccessible(true);
        demonstrateMethod.invoke(instance);
    }

    private void handleException(Exception e, TestResult result) {
        result.setSuccess(false);

        String errorType = errorClassifier.classify(e);
        result.setErrorType(errorType);

        String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        result.setErrorMessage(errorMessage);

        logger.error("Example execution failed with {}: {}", errorType, errorMessage, e);
    }
}
