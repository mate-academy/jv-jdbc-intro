package mate.academy;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public DataProcessingException(String msg) {
        super(msg);
    }
}
