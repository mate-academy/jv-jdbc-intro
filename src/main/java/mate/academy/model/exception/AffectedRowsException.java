package mate.academy.model.exception;

public class AffectedRowsException extends RuntimeException {
    public AffectedRowsException(String message) {
        super(message);
    }
}
