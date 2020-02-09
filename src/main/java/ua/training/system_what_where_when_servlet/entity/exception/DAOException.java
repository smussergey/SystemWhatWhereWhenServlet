package ua.training.system_what_where_when_servlet.entity.exception;

public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }
}
