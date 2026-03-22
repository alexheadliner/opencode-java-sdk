# Plain Java Example

Demonstrates direct usage of the OpenCode SDK without Spring Boot.

## Purpose

This example shows how to use the OpenCode SDK in a plain Java application, without any Spring Boot dependencies. It demonstrates programmatic configuration and direct SDK client usage.

## Code Style Guidelines

### NO Lombok
This example follows the SDK pattern and does NOT use Lombok. Use explicit getters and setters when creating configuration classes.

### Main Class Structure
```java
public class Main {
    public static void main(String[] args) {
        // 1. Create configuration
        OpenCodeConfig config = new OpenCodeConfig();
        config.setBaseUrl("https://api.opencode.dev");
        config.setApiKey("your-api-key");
        
        // 2. Create client
        OpenCodeClient client = new OpenCodeClient(config);
        
        // 3. Use client
        ApiResponse response = client.get("/v1/resources");
        System.out.println("Status: " + response.getStatus());
    }
}
```

### Key Principles
1. Keep main method simple and linear
2. Add comments explaining configuration values
3. Use meaningful variable names
4. Handle exceptions gracefully
5. Print results to console for demonstration

## Dependencies

| Dependency | Scope | Purpose |
|------------|-------|---------|
| OpenCode SDK | compile | Core SDK library |
| JUnit Jupiter | test | Unit testing |
| AssertJ | test | Fluent assertions |

## Build and Run

### Build
```bash
# From project root
cd examples/plain-java
mvn clean package

# Or from this directory
mvn clean package
```

### Run
```bash
# Run the packaged JAR
java -jar target/opencode-examples-plain-java-0.1.0-SNAPSHOT.jar

# Or run main class directly
mvn exec:java -Dexec.mainClass="opencode.examples.plainjava.Main"
```

## Configuration

The example uses programmatic configuration:

```java
OpenCodeConfig config = new OpenCodeConfig();
config.setBaseUrl("https://api.opencode.dev");  // OpenCode server URL
config.setApiKey("your-api-key");               // API authentication
config.setTimeout(30);                          // Request timeout in seconds
```

## Expected Output

```
Status: 200
Data: {...}
Message: Success
```

## Extending the Example

To add new API calls:
1. Create new methods in the Main class
2. Use the existing OpenCodeClient instance
3. Print results to console

## Testing

- Do NOT create tests for this example
- Manual verification is sufficient
- Example is for demonstration only
