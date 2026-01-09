package mate.academy.exceptions;

public class DataProcessingException extends RuntimeException {
    private Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        super(message);
        this.ex = ex;
    }
}
