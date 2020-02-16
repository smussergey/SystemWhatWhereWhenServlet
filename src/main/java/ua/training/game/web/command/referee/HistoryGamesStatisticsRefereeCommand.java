package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.service.HistoryService;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.web.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HistoryGamesStatisticsRefereeCommand implements Command {
    private final static int DEFAULT_PAGINATION_SIZE = 2;
    private static final Logger LOGGER = Logger.getLogger(HistoryGamesStatisticsRefereeCommand.class);
    private final HistoryService historyService = new HistoryService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");
        int currentPage;

        if (request.getParameter("currentPage") == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.valueOf(request.getParameter("currentPage"));
        }

        Page<GameDTO> gameDTOPage = historyService.getHistoryGameStatisticsByAllGamesAndPlayers(currentPage, DEFAULT_PAGINATION_SIZE);

        request.setAttribute("gameDTOPage", gameDTOPage);

        LOGGER.info(String.format("histories were generated in amount = %d", gameDTOPage.getEntries().size()));

        return "/WEB-INF/referee/historyGamesStatisticsReferee.jsp";
    }
}
