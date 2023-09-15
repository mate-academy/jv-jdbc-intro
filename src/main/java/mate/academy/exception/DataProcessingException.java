package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String s, Throwable e) {
        super(s,e);
    }
}
