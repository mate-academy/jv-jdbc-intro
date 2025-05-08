package mate.academy.exeption;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable ex) {
        super(massage, ex);
    }

    public DataProcessingException(String massage) {
        super(massage);
    }
}
