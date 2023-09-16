package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public <T> DataProcessingException(String message, T ex) {
        super(message);
    }
}
