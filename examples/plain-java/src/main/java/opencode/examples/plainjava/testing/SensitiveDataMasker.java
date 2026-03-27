package opencode.examples.plainjava.testing;

public class SensitiveDataMasker {

    private static final String MASK = "***";

    public static String maskPassword(String text, String password) {
        if (text == null || password == null || password.isEmpty()) {
            return text;
        }
        return text.replace(password, MASK);
    }

    public static String maskApiKey(String text, String apiKey) {
        if (text == null || apiKey == null || apiKey.isEmpty()) {
            return text;
        }
        return text.replace(apiKey, MASK);
    }

    public static String maskAllSensitiveData(String text, TestConfiguration config) {
        if (text == null || config == null) {
            return text;
        }

        String result = text;
        result = maskPassword(result, config.getPassword());
        result = maskApiKey(result, config.getProviderApiKey());
        return result;
    }
}
