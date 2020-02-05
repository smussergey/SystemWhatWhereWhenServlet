package ua.training.system_what_where_when_servlet.entity.exception;

public class NotUniqueLoginException extends RuntimeException {
    private String loginData;

    public String getLoginData() {
        return loginData;
    }

    public NotUniqueLoginException(String message, String loginData) {
        super(message);
        this.loginData = loginData;
    }
}
