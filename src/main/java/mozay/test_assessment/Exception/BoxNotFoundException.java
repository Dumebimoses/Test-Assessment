package mozay.test_assessment.Exception;

public class BoxNotFoundException extends RuntimeException {
    public BoxNotFoundException(String message) {
        super(message);
    }
    public BoxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

