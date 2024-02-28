package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}
