package ua.training.system_what_where_when_servlet.entity.exception;

public class TwoPlayersTheSameException extends RuntimeException {
    public TwoPlayersTheSameException(String message) {
        super(message);
    }
}
