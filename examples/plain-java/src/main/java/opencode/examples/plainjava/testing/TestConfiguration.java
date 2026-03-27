package opencode.examples.plainjava.testing;

import java.util.ArrayList;
import java.util.List;

public class TestConfiguration {

    private String baseUrl = "http://localhost:4096";
    private String username = "opencode";
    private String password = "opencode123";
    private String provider = "Z.AI";
    private String model = "GLM-4.7";
    private String providerApiKey;
    private boolean colorOutput = true;
    private String logFile;
    private List<String> exampleNames = new ArrayList<>();
    private boolean runAll = true;

    public TestConfiguration() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProviderApiKey() {
        return providerApiKey;
    }

    public void setProviderApiKey(String providerApiKey) {
        this.providerApiKey = providerApiKey;
    }

    public boolean isColorOutput() {
        return colorOutput;
    }

    public void setColorOutput(boolean colorOutput) {
        this.colorOutput = colorOutput;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public List<String> getExampleNames() {
        return exampleNames;
    }

    public void setExampleNames(List<String> exampleNames) {
        this.exampleNames = exampleNames;
    }

    public boolean isRunAll() {
        return runAll;
    }

    public void setRunAll(boolean runAll) {
        this.runAll = runAll;
    }

    public void validate() {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Base URL cannot be null or empty");
        }
        if (!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://")) {
            throw new IllegalArgumentException("Base URL must start with http:// or https://");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}
