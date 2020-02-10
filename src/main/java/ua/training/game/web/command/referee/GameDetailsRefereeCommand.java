package ua.training.game.web.command.referee;

        import org.apache.log4j.Logger;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.service.GameInformationService;
import ua.training.game.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameDetailsRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GameDetailsRefereeCommand.class);
    private final GameInformationService gameInformationService = new GameInformationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GameDetailsRefereeCommand class is executing");
        int gameId = Integer.valueOf(request.getParameter("gameid"));
        LOGGER.info(String.format("GameDetailsRefereeCommand class: parameter id = %d  was received as request for a game details", gameId));

        try {
            GameDTO gameDTO = gameInformationService.getGameFullStatisticsByIdForReferee(gameId);
            LOGGER.info(String.format("GameDetailsRefereeCommand class: game details were generated for a game with id = %d", gameDTO.getId()));
            request.setAttribute("gameDTO", gameDTO);
            request.setAttribute("appealStageFiled",
                    ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));


        } catch (EntityNotFoundException ex) {
            //TODO
        }
        return "/WEB-INF/referee/gameDetailsReferee.jsp";
    }
}
