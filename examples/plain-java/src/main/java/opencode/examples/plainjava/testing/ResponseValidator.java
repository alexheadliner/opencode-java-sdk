package opencode.examples.plainjava.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ResponseValidator {

    private static final Logger logger = LoggerFactory.getLogger(ResponseValidator.class);

    public ValidationResult validate(Object response, String context) {
        if (response == null) {
            return ValidationResult.failure(context, "Response is null");
        }
        return ValidationResult.success("Response validated for " + context);
    }

    public ValidationResult validateNonNull(Object value, String fieldName) {
        if (value == null) {
            ValidationResult result = ValidationResult.failure(fieldName, "Field is null");
            logValidation(result);
            return result;
        }
        ValidationResult result = ValidationResult.success("Field " + fieldName + " is non-null");
        logValidation(result);
        return result;
    }

    public ValidationResult validateCollection(Collection<?> collection, String fieldName) {
        if (collection == null) {
            ValidationResult result = ValidationResult.failure(fieldName, "Collection is null");
            logValidation(result);
            return result;
        }

        if (collection.isEmpty()) {
            ValidationResult result = ValidationResult.success("Collection " + fieldName + " is empty but non-null");
            logValidation(result);
            return result;
        }

        // Check if all elements are non-null
        for (Object element : collection) {
            if (element == null) {
                ValidationResult result = ValidationResult.failure(fieldName, "Collection contains null element");
                logValidation(result);
                return result;
            }
        }

        ValidationResult result = ValidationResult.success("Collection " + fieldName + " has " + collection.size() + " valid elements");
        logValidation(result);
        return result;
    }

    public ValidationResult validateEnum(Enum<?> value, String fieldName) {
        if (value == null) {
            ValidationResult result = ValidationResult.failure(fieldName, "Enum value is null");
            logValidation(result);
            return result;
        }

        // Enum values are always valid if non-null (Java type system guarantees this)
        ValidationResult result = ValidationResult.success("Enum " + fieldName + " has valid value: " + value.name());
        logValidation(result);
        return result;
    }

    private void logValidation(ValidationResult result) {
        if (result.isValid()) {
            logger.debug("Validation success: {}", result.getMessage());
        } else {
            logger.warn("Validation failure: field={}, message={}", result.getFieldName(), result.getMessage());
        }
    }
}
