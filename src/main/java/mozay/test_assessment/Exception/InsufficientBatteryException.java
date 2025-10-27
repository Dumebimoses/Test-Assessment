package mozay.test_assessment.Exception;

public class InsufficientBatteryException extends RuntimeException {

    public InsufficientBatteryException(String message) {
        super(message);
    }

    public InsufficientBatteryException(String message, Throwable cause) {
        super(message, cause);
    }
}
