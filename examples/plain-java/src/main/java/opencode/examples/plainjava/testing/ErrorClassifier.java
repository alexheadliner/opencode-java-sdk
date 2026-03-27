package opencode.examples.plainjava.testing;

import opencode.sdk.invoker.ApiException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorClassifier {

    public static final String CONNECTION_ERROR = "ConnectionError";
    public static final String AUTHENTICATION_ERROR = "AuthenticationError";
    public static final String API_ERROR = "ApiError";
    public static final String VALIDATION_ERROR = "ValidationError";
    public static final String PARSE_ERROR = "ParseError";
    public static final String UNEXPECTED_ERROR = "UnexpectedError";

    public String classify(Exception exception) {
        if (exception == null) {
            return UNEXPECTED_ERROR;
        }

        // Check for connection-related errors
        if (isConnectionError(exception)) {
            return CONNECTION_ERROR;
        }

        // Check for API exceptions with status codes
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            int statusCode = apiException.getCode();

            if (statusCode == 401 || statusCode == 403) {
                return AUTHENTICATION_ERROR;
            } else if (statusCode >= 400 && statusCode < 600) {
                return API_ERROR;
            }
        }

        // Check for validation errors
        if (exception instanceof IllegalArgumentException ||
                exception instanceof IllegalStateException) {
            return VALIDATION_ERROR;
        }

        // Check for parse errors
        if (exception.getMessage() != null &&
                (exception.getMessage().contains("JSON") ||
                        exception.getMessage().contains("parse") ||
                        exception.getMessage().contains("deserialize"))) {
            return PARSE_ERROR;
        }

        return UNEXPECTED_ERROR;
    }

    private boolean isConnectionError(Exception exception) {
        if (exception instanceof ConnectException ||
                exception instanceof SocketTimeoutException ||
                exception instanceof UnknownHostException) {
            return true;
        }

        if (exception instanceof IOException) {
            String message = exception.getMessage();
            if (message != null) {
                String lowerMessage = message.toLowerCase();
                return lowerMessage.contains("connection") ||
                        lowerMessage.contains("timeout") ||
                        lowerMessage.contains("refused") ||
                        lowerMessage.contains("unreachable");
            }
        }

        Throwable cause = exception.getCause();
        if (cause instanceof Exception) {
            return isConnectionError((Exception) cause);
        }

        return false;
    }
}
