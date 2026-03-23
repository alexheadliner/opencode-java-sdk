# Plain Java Example

Demonstrates direct usage of the OpenCode SDK without Spring Boot.

## Purpose

This example shows how to use the OpenCode SDK in a plain Java application with 18 comprehensive examples covering all SDK features.

## Project Structure

```
src/main/java/opencode/examples/plainjava/
├── Main.java                      # Entry point - runs all examples
├── ConfigurationExample.java      # Configuration management
├── DevToolsExample.java           # LSP and formatter status
├── EventStreamingExample.java     # SSE event streaming
├── ExperimentalExample.java       # Workspace and worktree operations
├── FileOperationsExample.java     # File tree, content, search
├── InstanceExample.java           # Instance management
├── InteractiveExample.java        # Questions and permissions
├── McpExample.java                # MCP server management
├── MessageExample.java            # Session messaging
├── ProjectExample.java            # Project operations
├── ProviderExample.java           # Provider configuration
├── PtyExample.java                # PTY terminal operations
├── SessionAdvancedExample.java    # Fork, revert, summarize
├── SessionCrudExample.java        # Session CRUD operations
├── SystemInfoExample.java         # Health and skills
├── TodoExample.java               # Todo management
└── VcsExample.java                # Version control info
```

## Example Classes by Category

### System & Configuration
| Example | Description |
|---------|-------------|
| **SystemInfoExample** | Health checks and app skills |
| **ConfigurationExample** | Global and project configuration |
| **ProviderExample** | Provider listing and OAuth |
| **ProjectExample** | Project info and updates |

### Session Management
| Example | Description |
|---------|-------------|
| **SessionCrudExample** | Create, read, update, delete sessions |
| **SessionAdvancedExample** | Fork, revert, share, summarize sessions |
| **MessageExample** | Send prompts and receive responses |

### File Operations
| Example | Description |
|---------|-------------|
| **FileOperationsExample** | File tree, content, search, symbols |

### Development Tools
| Example | Description |
|---------|-------------|
| **DevToolsExample** | LSP and formatter status |
| **ExperimentalExample** | Workspace and worktree management |

### Instance & Interactive
| Example | Description |
|---------|-------------|
| **InstanceExample** | Server instance management |
| **InteractiveExample** | Questions and permissions handling |

### MCP & Extensions
| Example | Description |
|---------|-------------|
| **McpExample** | MCP server configuration and auth |
| **TodoExample** | Session todo management |
| **VcsExample** | Version control information |

### Real-time
| Example | Description |
|---------|-------------|
| **EventStreamingExample** | Server-sent events |
| **PtyExample** | Pseudo-terminal operations |

## Running the Examples

### Build
```bash
cd examples/plain-java
mvn clean package
```

### Run
```bash
# Run Main.java which executes all examples
java -jar target/opencode-examples-plain-java-0.1.0-SNAPSHOT.jar

# Or with Maven
mvn exec:java -Dexec.mainClass="opencode.examples.plainjava.Main"
```

## Dependencies

| Dependency | Scope | Purpose |
|------------|-------|---------|
| OpenCode SDK | compile | Core SDK library |
| JUnit Jupiter | test | Unit testing |
| AssertJ | test | Fluent assertions |

## Code Style

- NO Lombok - explicit getters/setters only
- Plain Java 21 features
- SLF4J for logging
- Each example is self-contained

## Configuration

See [README.md](README.md) for detailed configuration instructions.

## Testing

- Do NOT create tests for examples
- Manual verification is sufficient
- Examples are for demonstration only
