package mozay.test_assessment.Exception;

public class WeightExceededException extends RuntimeException {

    public WeightExceededException(String message) {
        super(message);
    }

    public WeightExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}

