package samatov.jdbcProject.exception;

import java.sql.SQLException;

public class PostException extends RuntimeException {
    public PostException(String message) {
        super(message);
    }
}
