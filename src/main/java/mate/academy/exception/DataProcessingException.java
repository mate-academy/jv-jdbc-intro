package mate.academy.exception;

import mate.academy.lib.Dao;

@Dao
public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable exception) {
        super(message, exception);
    }
}
