package mate.academy;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
}
