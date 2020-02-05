package ua.training.system_what_where_when_servlet.controller.command.player;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(MainPlayerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("MainPlayerCommand class is executing");
        return "/WEB-INF/player/mainPlayer.jsp";
    }
}
