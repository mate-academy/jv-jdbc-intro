package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public <Trowable> DataProcessingException(String message, Trowable ex) {
        super(message);
    }
}
