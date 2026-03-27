package opencode.examples.plainjava.testing;

public class EnvironmentLoader {

    public void loadIntoConfiguration(TestConfiguration config) {
        // Load server configuration
        String serverUrl = getEnvOrDefault("OPENCODE_SERVER_URL", null);
        if (serverUrl != null && config.getBaseUrl().equals("http://localhost:4096")) {
            config.setBaseUrl(serverUrl);
        }

        String username = getEnvOrDefault("OPENCODE_SERVER_USERNAME", null);
        if (username != null && config.getUsername().equals("opencode")) {
            config.setUsername(username);
        }

        String password = getEnvOrDefault("OPENCODE_SERVER_PASSWORD", null);
        if (password != null && config.getPassword().equals("opencode123")) {
            config.setPassword(password);
        }

        // Load LLM configuration
        String provider = getEnvOrDefault("OPENCODE_LLM_PROVIDER", null);
        if (provider != null && config.getProvider().equals("Z.AI")) {
            config.setProvider(provider);
        }

        String model = getEnvOrDefault("OPENCODE_LLM_MODEL", null);
        if (model != null && config.getModel().equals("GLM-4.7")) {
            config.setModel(model);
        }

        // Load provider API keys
        if (config.getProviderApiKey() == null) {
            String apiKey = loadProviderApiKey(config.getProvider());
            if (apiKey != null) {
                config.setProviderApiKey(apiKey);
            }
        }
    }

    private String loadProviderApiKey(String provider) {
        if (provider == null) {
            return null;
        }

        switch (provider.toUpperCase()) {
            case "Z.AI":
            case "ZAI":
                return getEnvOrDefault("Z_AI_API_KEY", null);
            case "OPENAI":
                return getEnvOrDefault("OPENAI_API_KEY", null);
            case "ANTHROPIC":
                return getEnvOrDefault("ANTHROPIC_API_KEY", null);
            default:
                // Try generic pattern: PROVIDER_NAME_API_KEY
                String envVarName = provider.toUpperCase().replace(".", "_").replace("-", "_") + "_API_KEY";
                return getEnvOrDefault(envVarName, null);
        }
    }

    private String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}
