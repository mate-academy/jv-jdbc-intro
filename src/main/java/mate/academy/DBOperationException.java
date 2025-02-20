package mate.academy;

public class DBOperationException extends RuntimeException {
    public DBOperationException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public DBOperationException(String message) {
        super(message);
    }
}
