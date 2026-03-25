# OpenCode Examples

Example applications demonstrating OpenCode SDK usage patterns.

## Purpose

This module aggregates example applications showing different ways to use the OpenCode SDK:
- **Plain Java Example**: Direct SDK usage without Spring Boot
- **Spring Boot Example**: Integration using the Spring Boot Starter

## Module Structure

```
examples/
├── pom.xml                           # Examples parent POM
├── AGENTS.md                         # This documentation
├── plain-java/                       # Plain Java example
│   ├── pom.xml
│   ├── README.md                     # Comprehensive run instructions
│   ├── TESTING-PLAN.md               # Testing documentation
│   └── src/main/java/opencode/examples/plainjava/
│       ├── Main.java                 # Entry point
│       ├── ConfigurationExample.java
│       ├── DevToolsExample.java
│       ├── EventStreamingExample.java
│       ├── ExperimentalExample.java
│       ├── FileOperationsExample.java
│       ├── InstanceExample.java
│       ├── InteractiveExample.java
│       ├── McpExample.java
│       ├── MessageExample.java
│       ├── ProjectExample.java
│       ├── ProviderExample.java
│       ├── PtyExample.java
│       ├── SessionAdvancedExample.java
│       ├── SessionCrudExample.java
│       ├── SystemInfoExample.java
│       ├── TodoExample.java
│       └── VcsExample.java
└── spring-boot/                      # Spring Boot example
    ├── pom.xml
    ├── AGENTS.md                     # Controller documentation
    └── src/
        ├── main/java/opencode/examples/springboot/
        │   ├── OpenCodeSpringBootApplication.java
        │   └── controller/
        │       ├── ConfigurationController.java
        │       ├── DevToolsController.java
        │       ├── EventStreamingController.java
        │       ├── ExperimentalController.java
        │       ├── FileOperationsController.java
        │       ├── InstanceController.java
        │       ├── InteractiveController.java
        │       ├── MessageController.java
        │       ├── McpController.java
        │       ├── ProjectController.java
        │       ├── ProviderController.java
        │       ├── PtyController.java
        │       ├── SessionAdvancedController.java
        │       ├── SessionCrudController.java
        │       ├── SystemInfoController.java
        │       ├── TodoController.java
        │       └── VcsController.java
        └── test/
            ├── java/opencode/examples/springboot/
            │   ├── controller/
            │   │   ├── ConfigurationControllerIT.java
            │   │   ├── DevToolsControllerIT.java
            │   │   ├── FileOperationsControllerIT.java
            │   │   ├── InstanceControllerIT.java
            │   │   ├── MessageControllerIT.java
            │   │   ├── ProjectControllerIT.java
            │   │   ├── ProviderControllerIT.java
            │   │   ├── SessionAdvancedControllerIT.java
            │   │   ├── SessionCrudControllerIT.java
            │   │   ├── SystemInfoControllerIT.java
            │   │   ├── TodoControllerIT.java
            │   │   └── VcsControllerIT.java
            │   └── testsupport/
            │       ├── AbstractIntegrationTest.java
            │       └── OpenCodeServerContainer.java
            └── resources/
                └── application-test.properties
```

## Build Configuration

### Parent POM
- **Artifact ID**: `opencode-examples`
- **Packaging**: `pom`
- **Modules**: `plain-java`, `spring-boot`

### Build Commands

```bash
# Build all examples
cd examples && mvn clean package

# Build specific example
cd examples/plain-java && mvn clean package
cd examples/spring-boot && mvn clean package

# Run tests for Spring Boot example
cd examples/spring-boot && mvn test

# Run plain Java example
java -jar examples/plain-java/target/opencode-examples-plain-java-0.1.0-SNAPSHOT.jar
```

## Example Guidelines

### General Principles
1. Keep examples simple and focused
2. Demonstrate one concept per example
3. Include clear comments for configuration values
4. Show both programmatic and configuration-based setup

### Code Style
- Follow conventions of the respective module (Lombok for Spring Boot, plain Java for SDK examples)
- Use meaningful variable names
- Add comments explaining configuration values
- Keep example code under 100 lines when possible

## Plain Java Example

See `plain-java/AGENTS.md` for specific guidelines.

## Spring Boot Example

The Spring Boot example demonstrates comprehensive REST API coverage with 17 controllers wrapping the OpenCode SDK.

### Controllers Implemented

| Category | Controllers |
|----------|-------------|
| **System & Configuration** | SystemInfoController, ConfigurationController, ProviderController, ProjectController |
| **File Operations** | FileOperationsController |
| **Session Management** | SessionCrudController, SessionAdvancedController, MessageController |
| **Development Tools** | DevToolsController, ExperimentalController |
| **Instance & Interactive** | InstanceController, InteractiveController |
| **MCP & Extensions** | McpController, TodoController, VcsController |
| **Real-time & Streaming** | EventStreamingController, PtyController |

### Running the Example

```bash
cd examples/spring-boot
mvn spring-boot:run
```

Application runs on port 8081. Access endpoints at `http://localhost:8081/api/...`

See [`spring-boot/AGENTS.md`](spring-boot/AGENTS.md) for complete controller documentation and HTTP test files.

## Testing Examples

### Plain Java Example
- Examples are for demonstration purposes only
- Manual verification is sufficient

### Spring Boot Example
The Spring Boot example includes comprehensive integration tests using TestContainers:

**Test Dependencies**
- **Spring Boot Starter Test**: JUnit 5, AssertJ, Spring Test
- **TestContainers**: Integration testing with Docker containers
- **Maven Surefire Plugin**: Test execution configuration

**Test Infrastructure**

| Class | Purpose |
|-------|---------|
| `AbstractIntegrationTest` | Base class for all integration tests with TestContainers setup |
| `OpenCodeServerContainer` | TestContainer wrapper for OpenCode server Docker image |
| `application-test.properties` | Test profile configuration |

**Integration Test Coverage**

| Controller | Test Class |
|------------|------------|
| ConfigurationController | `ConfigurationControllerIT` |
| DevToolsController | `DevToolsControllerIT` |
| FileOperationsController | `FileOperationsControllerIT` |
| InstanceController | `InstanceControllerIT` |
| MessageController | `MessageControllerIT` |
| ProjectController | `ProjectControllerIT` |
| ProviderController | `ProviderControllerIT` |
| SessionAdvancedController | `SessionAdvancedControllerIT` |
| SessionCrudController | `SessionCrudControllerIT` |
| SystemInfoController | `SystemInfoControllerIT` |
| TodoController | `TodoControllerIT` |
| VcsController | `VcsControllerIT` |

**Running Tests**

```bash
# Run all tests
cd examples/spring-boot && mvn test

# Run integration tests only
cd examples/spring-boot && mvn test -DrunIntegrationTests

# Run specific test class
mvn test -Dtest=ConfigurationControllerIT
```

**Test Configuration**
- Tests use a randomized server port to avoid conflicts
- TestContainers manages Docker container lifecycle
- OpenCode server container is reused across tests for performance

## Documentation

Each example should include:
1. README with setup instructions
2. Comments explaining configuration
3. Example requests/responses
