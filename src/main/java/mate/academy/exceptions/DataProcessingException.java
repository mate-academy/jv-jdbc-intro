package mate.academy.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
