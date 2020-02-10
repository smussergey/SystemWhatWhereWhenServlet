package ua.training.game.web.command.player;

import org.apache.log4j.Logger;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.service.AppealService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileAppealFormPlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FileAppealFormPlayerCommand.class);
    private final AppealService appealService = new AppealService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("GetFileAppealForm class is executing");
        int gameId = Integer.valueOf(request.getParameter("gameid"));
        LOGGER.info(String.format("GetFileAppealForm class: parameter id = %d  was received as request for a game details", gameId));

        String username = (String) request.getSession().getAttribute("username"); //TODO check casting

        try {
            GameDTO gameDTO = appealService.getGameInformationByIdForFileAppealForm(gameId, username);
            LOGGER.info(String.format("GetFileAppealForm class: game details were generated for a game with id = %d", gameDTO.getId()));
            request.setAttribute("gameDTO", gameDTO);
        } catch (EntityNotFoundException ex) {
            //TODO
        }
        return "/WEB-INF/player/fileAppealFormPlayer.jsp";
    }
}
