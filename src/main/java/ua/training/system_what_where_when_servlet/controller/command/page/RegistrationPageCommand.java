package ua.training.system_what_where_when_servlet.controller.command.page;

import ua.training.system_what_where_when_servlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "registration.jsp";
    }
}
