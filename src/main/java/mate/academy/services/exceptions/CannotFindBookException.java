package mate.academy.services.exceptions;

public class CannotFindBookException extends RuntimeException {
    public CannotFindBookException(String message) {
        super(message);
    }
}
