package lib;

import java.sql.SQLException;

public class DataProcessingException extends SQLException {
    public DataProcessingException(String message, SQLException e) {
        super(message);
    }
}
