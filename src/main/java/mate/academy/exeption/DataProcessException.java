package mate.academy.exeption;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable exeption) {
        super(message, exeption);
    }
}
