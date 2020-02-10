package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.UserDTO;
import ua.training.game.enums.Role;
import ua.training.game.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class NewGamePrepareRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(NewGamePrepareRefereeCommand.class);
    UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("NewGamePrepareRefereeCommand class is executing");

        List<UserDTO> players = userService.getAllUserDTOsByRole(Role.ROLE_PLAYER);
        request.setAttribute("players", players);
        return "/WEB-INF/referee/newGameReferee.jsp";
    }
}
