package mate.academy;

import java.sql.SQLException;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, SQLException e) {
        super(massage);
    }
}
