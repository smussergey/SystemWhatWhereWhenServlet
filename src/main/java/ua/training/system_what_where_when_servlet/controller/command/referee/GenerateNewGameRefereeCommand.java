package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.service.NewGameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateNewGameRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GenerateNewGameRefereeCommand.class);
    private NewGameService newGameService = new NewGameService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GenerateNewGameRefereeCommand class is executing");
        // TODO validatiion
        int firstPlayerId = Integer.valueOf(request.getParameter("firstplayerid"));
        int secondPlayerId = Integer.valueOf(request.getParameter("secondplayerid"));
        int maxscores = Integer.valueOf(request.getParameter("maxscores"));
        LOGGER.info(String.format("In GenerateNewGameRefereeCommand, data: playerId = %S, opponentId = %S, maxscores = %S,", firstPlayerId, secondPlayerId, maxscores));

        newGameService.runNewGame(firstPlayerId, secondPlayerId, maxscores);

        return "/referee/gamesStatisticsReferee"; // TODO improve
    }
}
