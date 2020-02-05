package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoveToHistoryRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(MoveToHistoryRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");
        int gameId = Integer.valueOf(request.getParameter("gameid"));
        LOGGER.info(String.format("parameter id = %d  was received as request for a game", gameId));

        ServiceFactory.getInstance().getHistoryService().moveGameToHistoryAndDeleteGameRecords(gameId);

        return "/referee/historyConsiderationReferee";
    }
}
