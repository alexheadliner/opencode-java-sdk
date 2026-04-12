# OpenCode Spring Boot Starter

Spring Boot auto-configuration module for OpenCode SDK integration.

## Purpose

This module provides Spring Boot auto-configuration for the OpenCode SDK, allowing easy integration into Spring Boot applications with minimal configuration. It uses Lombok for boilerplate code reduction.

## Architecture

```mermaid
flowchart TB
    subgraph "Spring Boot Starter"
        AUTO["OpenCodeAutoConfiguration<br/>@AutoConfiguration"]
        PROPS["OpenCodeProperties<br/>@ConfigurationProperties"]
        SERVICE["OpenCodeService<br/>@Service"]
    end

    subgraph "SDK Integration"
        CLIENT["OpenCodeClient<br/>From SDK"]
        CONFIG["OpenCodeConfig<br/>From SDK"]
    end

    subgraph "Spring Boot"
        SPRING["Spring Application Context"]
        BEANS["Auto-registered Beans"]
    end

    AUTO --> PROPS
    AUTO --> CONFIG
    AUTO --> CLIENT
    AUTO --> SERVICE
    SERVICE --> CLIENT
    CLIENT --> CONFIG
    SPRING --> BEANS
    BEANS --> AUTO

    style AUTO fill:#fff3e0
    style PROPS fill:#e3f2fd
    style SERVICE fill:#e8f5e9
    style CLIENT fill:#fce4ec
    style CONFIG fill:#fce4ec
```

## Key Classes

| Class | Package | Description |
|-------|---------|-------------|
| `OpenCodeAutoConfiguration` | `opencode.sdk.springboot.autoconfigure` | Auto-configuration class creating SDK beans |
| `OpenCodeProperties` | `opencode.sdk.springboot.autoconfigure` | Configuration properties binding with `opencode.*` prefix |
| `OpenCodeService` | `opencode.sdk.springboot` | Spring-managed service wrapper for SDK client |

## Code Style Guidelines

### Lombok Usage
This module USES Lombok. Use annotations for boilerplate reduction:

```java
// CORRECT - Use Lombok
@Getter
@Setter
@ConfigurationProperties(prefix = "opencode")
public class OpenCodeProperties {
    private String baseUrl = "http://localhost:4096";
    private String username;
    private String password;
    private int timeout = 30;
}

// Service with constructor injection
@Service
@RequiredArgsConstructor
public class OpenCodeService {
    private final DefaultApi defaultApi;
    private final ApiClient apiClient;
}
```

### Auto-Configuration Patterns

1. **Use @ConditionalOnMissingBean**
   ```java
   @Bean
   @ConditionalOnMissingBean
   public ApiClient apiClient(OpenCodeConfig config) {
       ApiClient client = new ApiClient();
       client.updateBaseUri(config.getBaseUrl());
       return client;
   }
   ```

2. **Enable Configuration Properties**
   ```java
   @AutoConfiguration
   @EnableConfigurationProperties(OpenCodeProperties.class)
   public class OpenCodeAutoConfiguration {
       // ...
   }
   ```

3. **Constructor Injection**
   ```java
   @AutoConfiguration
   @RequiredArgsConstructor
   public class OpenCodeAutoConfiguration {
       private final OpenCodeProperties properties;
   }
   ```

### Configuration Properties

Prefix all properties with `opencode.*`:

```yaml
opencode:
  base-url: http://localhost:4096
  username: opencode
  password: opencode123
  timeout: 30
```

### Package Structure
```
opencode.sdk.springboot/
├── OpenCodeService.java
└── autoconfigure/
    ├── OpenCodeAutoConfiguration.java
    └── OpenCodeProperties.java
```

## Dependencies

| Dependency | Version | Scope | Purpose |
|------------|---------|-------|---------|
| OpenCode SDK | ${project.version} | compile | Core SDK library |
| Spring Boot Starter Web | 3.5.13 | compile | Spring Boot web support |
| Spring Boot Configuration Processor | 3.5.13 | provided | Configuration metadata |
| Lombok | 1.18.36 | provided | Boilerplate reduction |

## Auto-Configuration Registration

The starter registers auto-configuration via:
- File: `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- Content: `opencode.sdk.springboot.autoconfigure.OpenCodeAutoConfiguration`

## Build Commands

```bash
# Compile starter module
mvn clean compile

# Install to local repository
mvn clean install

# Skip tests
mvn clean install -DskipTests
```

## Configuration Metadata

The configuration processor generates metadata in:
- `target/classes/META-INF/spring-configuration-metadata.json`

This enables IDE auto-completion for `opencode.*` properties.

## Usage in Applications

### Maven Dependency

```xml
<dependency>
    <groupId>io.opencode</groupId>
    <artifactId>opencode-spring-boot-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

### application.yml Configuration

```yaml
opencode:
  base-url: http://localhost:4096
  username: ${OPENCODE_USERNAME}
  password: ${OPENCODE_PASSWORD}
  timeout: 30
```

### Service Injection

```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final OpenCodeService openCodeService;

    public void doSomething() throws ApiException {
        GlobalHealth200Response health = openCodeService.getHealth();
        DefaultApi api = openCodeService.api();
    }
}
```

## Testing

- Do NOT create tests until directly asked
- When testing auto-configuration, use `@TestConfiguration`
- Mock `ApiClient` or `DefaultApi` for unit tests
- Use `@SpringBootTest` for integration tests

## Spring Boot Best Practices

1. **Conditional Beans**: Use `@ConditionalOnMissingBean` to allow override
2. **Configuration Properties**: Use relaxed binding (kebab-case in YAML, camelCase in Java)
3. **Default Values**: Provide sensible defaults in `OpenCodeProperties`
4. **Validation**: Use JSR-303 annotations on properties when needed
5. **Documentation**: Keep property descriptions in configuration metadata

## Integration with SDK

The starter depends on the SDK module and:
1. Creates `OpenCodeConfig` bean from properties
2. Creates `ApiClient` bean with Basic Auth configuration
3. Creates `DefaultApi` bean from `ApiClient`
4. Exposes `OpenCodeService` as a convenience wrapper

## Version Compatibility

- Spring Boot: 3.5.13+
- Java: 21+
- Aligns with SDK module version
