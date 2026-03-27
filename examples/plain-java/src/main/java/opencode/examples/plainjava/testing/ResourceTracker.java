package opencode.examples.plainjava.testing;

import java.util.ArrayList;
import java.util.List;

public class ResourceTracker {

    private final List<TrackedResource> resources = new ArrayList<>();

    public void trackSession(String sessionId) {
        trackResource("session", sessionId);
    }

    public void trackFile(String filePath) {
        trackResource("file", filePath);
    }

    public void trackResource(String type, String identifier) {
        resources.add(new TrackedResource(type, identifier));
    }

    public List<TrackedResource> getResources() {
        return new ArrayList<>(resources);
    }

    public void clear() {
        resources.clear();
    }

    public int getResourceCount() {
        return resources.size();
    }
}
