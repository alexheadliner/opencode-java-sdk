package opencode.examples.plainjava.testing;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private String exampleName;
    private boolean success;
    private long executionTimeMs;
    private String errorMessage;
    private String errorType;
    private String provider;
    private String model;
    private List<ValidationResult> validations = new ArrayList<>();

    public TestResult() {
    }

    public TestResult(String exampleName) {
        this.exampleName = exampleName;
    }

    public String getExampleName() {
        return exampleName;
    }

    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<ValidationResult> getValidations() {
        return validations;
    }

    public void setValidations(List<ValidationResult> validations) {
        this.validations = validations;
    }

    public void addValidation(ValidationResult validation) {
        this.validations.add(validation);
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

    @Override
    public String toString() {
        return "TestResult{" +
                "exampleName='" + exampleName + '\'' +
                ", success=" + success +
                ", executionTimeMs=" + executionTimeMs +
                ", errorType='" + errorType + '\'' +
                ", validations=" + validations.size() +
                '}';
    }
}
