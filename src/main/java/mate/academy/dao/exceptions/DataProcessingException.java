package mate.academy.dao.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message,Throwable ex) {
        super(message, ex);
    }
}
