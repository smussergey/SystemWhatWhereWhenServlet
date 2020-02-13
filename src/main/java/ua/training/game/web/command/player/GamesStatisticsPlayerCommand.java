package ua.training.game.web.command.player;

import org.apache.log4j.Logger;
import ua.training.game.service.GameInformationService;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GamesStatisticsPlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GamesStatisticsPlayerCommand.class);
    private final GameInformationService gameInformationService = new GameInformationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GamesStatisticsPlayerCommand class is executing");

        String username = (String) request.getSession().getAttribute("username"); //check casting

        List<GameDTO> gameDTOs = gameInformationService.getGamesStatisticsByLoggedInPlayer(username);
        request.setAttribute("gameDTOs", gameDTOs);
        LOGGER.info(String.format("GamesStatisticsPlayerCommand class: GameDTO were generated in amount = %d", gameDTOs.size()));
        return "/WEB-INF/player/gamesStatisticsPlayer.jsp";
    }
}