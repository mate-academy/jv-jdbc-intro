package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
