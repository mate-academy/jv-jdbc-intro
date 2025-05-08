package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String cause, Throwable e) {
        super(cause, e);
    }

    public DataProcessingException(String cause) {
        super(cause);
    }
}
