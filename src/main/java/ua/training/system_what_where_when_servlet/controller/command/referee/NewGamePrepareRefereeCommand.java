package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.dto.GameDTO;
import ua.training.system_what_where_when_servlet.dto.UserDTO;
import ua.training.system_what_where_when_servlet.entity.Role;
import ua.training.system_what_where_when_servlet.service.GameStatisticsAndDetailsService;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;
import ua.training.system_what_where_when_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class NewGamePrepareRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(NewGamePrepareRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("NewGamePrepareRefereeCommand class is executing");

        UserService userService = ServiceFactory.getInstance().getUserService();
        List<UserDTO> players = userService.getAllUserDTOsByRole(Role.ROLE_PLAYER);
        request.setAttribute("players", players);
        return "/WEB-INF/referee/newGameReferee.jsp";
    }
}
