package mate.academy.exception;

public class DataException extends RuntimeException {
    public DataException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
