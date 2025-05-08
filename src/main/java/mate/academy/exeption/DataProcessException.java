package mate.academy.exeption;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable exception) {
        super(message, exception);
    }
}
