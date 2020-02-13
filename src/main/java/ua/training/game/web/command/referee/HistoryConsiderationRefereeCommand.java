package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.service.HistoryService;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HistoryConsiderationRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryConsiderationRefereeCommand.class);
    private final HistoryService historyService = new HistoryService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");

        List<GameDTO> gameDTOs = historyService.getGamesWhichCanBeMovedToHistory();
        request.setAttribute("gameDTOs", gameDTOs);
        LOGGER.info(String.format("gameDTOs were generated in amount = %d", gameDTOs.size()));

        return "/WEB-INF/referee/historyConsiderationReferee.jsp";
    }
}

