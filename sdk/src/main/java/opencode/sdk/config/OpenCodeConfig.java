package opencode.sdk.config;

public class OpenCodeConfig {

    private String baseUrl = "http://localhost:4096";
    private String username;
    private String password;

    public OpenCodeConfig() {
    }

    public OpenCodeConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public OpenCodeConfig(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
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
}
