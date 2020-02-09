package ua.training.system_what_where_when_servlet.controller.command.player;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomePlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HomePlayerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");
        return "/WEB-INF/player/homePlayer.jsp";
    }
}
