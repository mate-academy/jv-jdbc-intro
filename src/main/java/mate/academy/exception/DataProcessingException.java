package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    private String message;
    private Throwable ex;

    public DataProcessingException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

}
