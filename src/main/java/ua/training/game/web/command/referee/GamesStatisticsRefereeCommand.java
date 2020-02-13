package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.service.GameInformationService;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GamesStatisticsRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GamesStatisticsRefereeCommand.class);
    private final GameInformationService gameInformationService = new GameInformationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GamesStatisticsRefereeCommand class is executing");
        List<GameDTO> gameDTOs = gameInformationService.getGameStatisticsByAllGamesAndPlayers();
        request.setAttribute("gameDTOs", gameDTOs);
        LOGGER.info(String.format("GamesStatisticsRefereeCommand class: GameDTO were generated in amount = %d", gameDTOs.size()));
        return "/WEB-INF/referee/gamesStatisticsReferee.jsp";
    }
}