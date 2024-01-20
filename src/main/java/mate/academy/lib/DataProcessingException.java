package mate.academy.lib;

public class DataProcessingException extends RuntimeException {
    private final String message;
    private final Throwable ex;

    public DataProcessingException(String message, Throwable ex) {
        this.message = message;
        this.ex = ex;
    }
}
