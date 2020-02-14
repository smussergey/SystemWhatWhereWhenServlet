package ua.training.game.web.command;

import org.apache.log4j.Logger;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.service.UserService;
import ua.training.game.web.command.util.AuthUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User loggedUser;

        LOGGER.info("User with " + username + " and password " + password + " is trying to log in ");

        if (username == null || username.equals("") || password == null || password.equals("")) {
            request.setAttribute("errorIncorrectCredentials", true);
            return "/login.jsp";
        }

        try {
            loggedUser = userService.findByUsernameAndPassword(username, password);
        } catch (EntityNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            request.setAttribute("errorIncorrectCredentials", true);
            return "/login.jsp";
        }

//        if (CommandUtility.checkUserIsLogged(request, username)) {
//            LOGGER.warn("User " + username + " is logged in already");
//            return "/WEB-INF/error404.jsp";
//        }

        if (Role.ROLE_REFEREE.equals(loggedUser.getRole())) {
            AuthUtil.addUserAndRoleToSession(request, Role.ROLE_REFEREE, username);
            AuthUtil.addUserToContext(request, username);
            LOGGER.info("Referee " + username + " logged successfully.");
            return "redirect:/referee/mainReferee";
        } else if (Role.ROLE_PLAYER.equals(loggedUser.getRole())) {
            AuthUtil.addUserAndRoleToSession(request, Role.ROLE_PLAYER, username);
            AuthUtil.addUserToContext(request, username);
            LOGGER.info("Player " + username + " logged successfully.");
            return "redirect:/player/mainPlayer";
        } else {
            LOGGER.info("User with " + username + " can not log in");
            request.setAttribute("errorIncorrectCredentials", true);
            return "/login.jsp";
        }
    }

}

