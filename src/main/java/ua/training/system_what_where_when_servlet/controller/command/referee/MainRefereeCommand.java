package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(MainRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("MainRefereeCommand class is executing");
        return "/WEB-INF/referee/mainReferee.jsp";
    }
}
