package ua.training.system_what_where_when_servlet.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExceptionCommand implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        throw new RuntimeException("Generated exception");
    }
}
