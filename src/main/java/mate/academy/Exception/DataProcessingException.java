package mate.academy.Exception;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String message) {
        super(message);
    }
}
