package mate.academy;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable cause) {
        super(massage, cause);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}
