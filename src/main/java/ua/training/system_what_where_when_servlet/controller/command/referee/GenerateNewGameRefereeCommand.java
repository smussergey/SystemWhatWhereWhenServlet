package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;
import ua.training.system_what_where_when_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateNewGameRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GenerateNewGameRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GenerateNewGameRefereeCommand class is executing");
        // TODO validatiion
        int playerId = Integer.valueOf(request.getParameter("playerid"));
        int opponentId = Integer.valueOf(request.getParameter("opponentid"));
        int maxscores = Integer.valueOf(request.getParameter("maxscores"));
        LOGGER.info(String.format("In GenerateNewGameRefereeCommand, data: playerId = %S, opponentId = %S, maxscores = %S,", playerId, opponentId, maxscores));

        ServiceFactory.getInstance().getNewGameService().runNewGame(playerId, opponentId, maxscores);

        return "/referee/gamesStatisticsReferee"; // todo improve
    }
}
