package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.service.HistoryService;
import ua.training.game.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoveToHistoryRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(MoveToHistoryRefereeCommand.class);
    private final HistoryService historyService = new HistoryService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");
        int gameId = Integer.valueOf(request.getParameter("gameid"));
        LOGGER.info(String.format("parameter id = %d  was received as request for a game", gameId));

        historyService.moveGameToHistory(gameId);

        return "/referee/historyConsiderationReferee";
    }
}