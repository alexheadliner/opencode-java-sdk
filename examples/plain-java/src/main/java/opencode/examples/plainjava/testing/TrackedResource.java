package opencode.examples.plainjava.testing;

public class TrackedResource {

    private final String type;
    private final String identifier;
    private final long createdAt;

    public TrackedResource(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
        this.createdAt = System.currentTimeMillis();
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "TrackedResource{" +
                "type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
