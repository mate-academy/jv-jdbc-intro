package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String msg, Throwable exp) {
        super(msg, exp);
    }
}
