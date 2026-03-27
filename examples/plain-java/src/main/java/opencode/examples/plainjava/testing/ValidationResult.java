package opencode.examples.plainjava.testing;

public class ValidationResult {

    private final boolean valid;
    private final String message;
    private final String fieldName;

    private ValidationResult(boolean valid, String message, String fieldName) {
        this.valid = valid;
        this.message = message;
        this.fieldName = fieldName;
    }

    public static ValidationResult success(String message) {
        return new ValidationResult(true, message, null);
    }

    public static ValidationResult failure(String fieldName, String message) {
        return new ValidationResult(false, message, fieldName);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        if (valid) {
            return "ValidationResult{valid=true, message='" + message + "'}";
        } else {
            return "ValidationResult{valid=false, fieldName='" + fieldName + "', message='" + message + "'}";
        }
    }
}
