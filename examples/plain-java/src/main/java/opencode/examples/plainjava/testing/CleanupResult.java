package opencode.examples.plainjava.testing;

import java.util.ArrayList;
import java.util.List;

public class CleanupResult {

    private final int totalResources;
    private final int cleanedResources;
    private final int failedResources;
    private final List<String> failures;

    public CleanupResult(int totalResources, int cleanedResources, int failedResources, List<String> failures) {
        this.totalResources = totalResources;
        this.cleanedResources = cleanedResources;
        this.failedResources = failedResources;
        this.failures = failures != null ? new ArrayList<>(failures) : new ArrayList<>();
    }

    public int getTotalResources() {
        return totalResources;
    }

    public int getCleanedResources() {
        return cleanedResources;
    }

    public int getFailedResources() {
        return failedResources;
    }

    public List<String> getFailures() {
        return new ArrayList<>(failures);
    }

    public boolean hasFailures() {
        return failedResources > 0;
    }

    @Override
    public String toString() {
        return "CleanupResult{" +
                "totalResources=" + totalResources +
                ", cleanedResources=" + cleanedResources +
                ", failedResources=" + failedResources +
                ", failures=" + failures +
                '}';
    }
}
