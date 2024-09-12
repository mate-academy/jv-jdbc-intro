package mate.academy;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        this.message = message;
        this.ex = ex;
    }
}
