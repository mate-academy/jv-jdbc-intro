package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable exception;

    public DataProcessingException(String message, Throwable exception) {
        this.message = message;
        this.exception = exception;
    }

}
