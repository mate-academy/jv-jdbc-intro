package mate.academy.costomexeption;

import java.sql.SQLException;

public class DataProcessingException extends Exception {
    public DataProcessingException(String exceptionInformation, SQLException e) {
        super(exceptionInformation, e);
    }

    public DataProcessingException(String exceptionInformation) {
        super(exceptionInformation);
    }
}
