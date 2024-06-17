package samatov.jdbcProject.exception;

import java.sql.SQLException;

public class WriterException extends RuntimeException {
    public WriterException(String message) {
        super(message);
    }
}
