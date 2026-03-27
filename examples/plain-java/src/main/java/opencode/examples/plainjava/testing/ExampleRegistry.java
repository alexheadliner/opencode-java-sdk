package opencode.examples.plainjava.testing;

import opencode.examples.plainjava.*;

import java.util.*;

public class ExampleRegistry {

    private final Map<String, Class<?>> exampleClasses = new LinkedHashMap<>();
    private final Map<String, Set<String>> apiCoverage = new LinkedHashMap<>();

    public ExampleRegistry() {
        registerAllExamples();
        mapApiCoverage();
    }

    private void registerAllExamples() {
        registerExample("SystemInfo", SystemInfoExample.class);
        registerExample("Configuration", ConfigurationExample.class);
        registerExample("Provider", ProviderExample.class);
        registerExample("Project", ProjectExample.class);
        registerExample("FileOperations", FileOperationsExample.class);
        registerExample("SessionCrud", SessionCrudExample.class);
        registerExample("SessionAdvanced", SessionAdvancedExample.class);
        registerExample("Message", MessageExample.class);
        registerExample("DevTools", DevToolsExample.class);
        registerExample("Experimental", ExperimentalExample.class);
        registerExample("Instance", InstanceExample.class);
        registerExample("Interactive", InteractiveExample.class);
        registerExample("Mcp", McpExample.class);
        registerExample("Todo", TodoExample.class);
        registerExample("Vcs", VcsExample.class);
        registerExample("EventStreaming", EventStreamingExample.class);
        registerExample("Pty", PtyExample.class);
    }

    private void mapApiCoverage() {
        // System & Global APIs
        apiCoverage.put("SystemInfo", Set.of("global/health", "app/agents", "app/skills", "command/list"));

        // Configuration APIs
        apiCoverage.put("Configuration", Set.of("config/get", "global/config/get", "config/providers", "config/update"));

        // Provider APIs
        apiCoverage.put("Provider", Set.of("provider/list", "provider/auth"));

        // Project APIs
        apiCoverage.put("Project", Set.of("project/list", "project/current", "project/update"));

        // File Operations APIs
        apiCoverage.put("FileOperations", Set.of("file/list", "file/read", "file/status", "find/files", "find/text", "find/symbols"));

        // Session CRUD APIs
        apiCoverage.put("SessionCrud", Set.of("session/list", "session/create", "session/get", "session/update", "session/delete"));

        // Session Advanced APIs
        apiCoverage.put("SessionAdvanced", Set.of("session/fork", "session/children", "session/share", "session/unshare",
                "session/summarize", "session/abort", "session/revert", "session/unrevert"));

        // Message APIs
        apiCoverage.put("Message", Set.of("session/prompt", "session/messages"));

        // DevTools APIs
        apiCoverage.put("DevTools", Set.of("lsp/status", "formatter/status"));

        // Experimental APIs
        apiCoverage.put("Experimental", Set.of("experimental/session/list", "experimental/workspace/list",
                "experimental/workspace/create", "experimental/resource/list"));

        // Instance APIs
        apiCoverage.put("Instance", Set.of("instance/dispose", "global/dispose"));

        // Interactive APIs
        apiCoverage.put("Interactive", Set.of("tool/ids", "tool/list", "question/list", "question/reply",
                "question/reject", "permission/list", "permission/reply"));

        // MCP APIs
        apiCoverage.put("Mcp", Set.of("mcp/status", "mcp/add", "mcp/connect", "mcp/auth/start"));

        // Todo APIs
        apiCoverage.put("Todo", Set.of("session/todo"));

        // VCS APIs
        apiCoverage.put("Vcs", Set.of("vcs/get", "worktree/list", "worktree/create", "worktree/remove"));

        // Event Streaming APIs
        apiCoverage.put("EventStreaming", Set.of("event/subscribe", "global/event"));

        // PTY APIs
        apiCoverage.put("Pty", Set.of("pty/list", "pty/create", "pty/get", "pty/update", "pty/remove"));
    }

    public void registerExample(String name, Class<?> exampleClass) {
        exampleClasses.put(name, exampleClass);
    }

    public Class<?> getExample(String name) {
        return exampleClasses.get(name);
    }

    public List<String> getAllExampleNames() {
        return new ArrayList<>(exampleClasses.keySet());
    }

    public boolean hasExample(String name) {
        return exampleClasses.containsKey(name);
    }

    public int getExampleCount() {
        return exampleClasses.size();
    }

    public Set<String> getApiEndpoints(String exampleName) {
        return apiCoverage.getOrDefault(exampleName, Collections.emptySet());
    }

    public Set<String> getAllCoveredEndpoints() {
        Set<String> allEndpoints = new HashSet<>();
        for (Set<String> endpoints : apiCoverage.values()) {
            allEndpoints.addAll(endpoints);
        }
        return allEndpoints;
    }

    public Map<String, Set<String>> getApiCoverageMap() {
        return new LinkedHashMap<>(apiCoverage);
    }
}
