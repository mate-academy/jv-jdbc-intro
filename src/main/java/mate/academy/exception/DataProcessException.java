package mate.academy.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessException(String message) {
        super(message);
    }
}
