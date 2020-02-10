package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HomeRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");
        return "/WEB-INF/referee/homeReferee.jsp";
    }
}
