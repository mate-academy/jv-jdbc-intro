package mate.academy.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable ex) {
        super(massage, ex);
    }

    public DataProcessingException(String massage) {
        super(massage);
    }
}
