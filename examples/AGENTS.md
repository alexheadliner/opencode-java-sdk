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
├── plain-java/                       # Plain Java example
│   ├── pom.xml
│   └── src/main/java/opencode/examples/plainjava/
│       └── Main.java
└── spring-boot/                      # Spring Boot example
    ├── pom.xml
    └── src/main/java/opencode/examples/springboot/
        ├── OpenCodeSpringBootApplication.java
        └── controller/
            └── OpenCodeController.java
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

See `spring-boot/AGENTS.md` for specific guidelines.

## Testing Examples

- Do NOT create tests for examples
- Examples are for demonstration purposes only
- Manual verification is sufficient

## Documentation

Each example should include:
1. README with setup instructions
2. Comments explaining configuration
3. Example requests/responses
