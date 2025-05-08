package mate.academy.dao;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
