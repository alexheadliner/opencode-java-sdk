package opencode.examples.plainjava.testing;

import opencode.sdk.client.OpenCodeClient;

public class ExampleContext {

    private final OpenCodeClient client;
    private final TestConfiguration config;
    private final ResourceTracker resourceTracker;
    private final ResponseValidator validator;

    public ExampleContext(OpenCodeClient client, TestConfiguration config,
                          ResourceTracker resourceTracker, ResponseValidator validator) {
        this.client = client;
        this.config = config;
        this.resourceTracker = resourceTracker;
        this.validator = validator;
    }

    public OpenCodeClient getClient() {
        return client;
    }

    public TestConfiguration getConfig() {
        return config;
    }

    public ResourceTracker getResourceTracker() {
        return resourceTracker;
    }

    public ResponseValidator getValidator() {
        return validator;
    }
}
