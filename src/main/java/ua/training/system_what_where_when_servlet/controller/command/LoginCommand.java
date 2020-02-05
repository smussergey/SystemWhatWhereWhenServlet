package ua.training.system_what_where_when_servlet.controller.command;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.entity.Role;
import ua.training.system_what_where_when_servlet.entity.User;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;
import ua.training.system_what_where_when_servlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LOGGER.info("User with " + username + " and password " + password + " is trying to log in ");

        if (username == null || username.equals("") || password == null || password.equals("")) {
            System.out.println("Error: fill out fields login, password");
            return "/login.jsp";
        }

//        if (CommandUtility.checkUserIsLogged(request, username)) {
//            LOGGER.warn("User " + username + " is logged in already");
//            return "/WEB-INF/error404.jsp";
//        }

        if (Role.ROLE_REFEREE.equals(getRoleByUsernameAndPassword(username, password))) {
            setUserAndRoleToSession(request, Role.ROLE_REFEREE, username);
            LOGGER.info("Referee " + username + " logged successfully.");
            return "/referee/mainReferee";
//            return   response.sendRedirect("/WEB-INF/referee/mainReferee.jsp");
        } else if (Role.ROLE_PLAYER.equals(getRoleByUsernameAndPassword(username, password))) {
            setUserAndRoleToSession(request, Role.ROLE_PLAYER, username);
            LOGGER.info("Player " + username + " logged successfully.");
            return "/player/mainPlayer";
        } else {
            LOGGER.info("User with " + username + " can not log in, try another passworn or username");
            return "/login.jsp";
        }
    }

    private Role getRoleByUsernameAndPassword(String username, String password) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        Optional<User> userOptional = userService.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            return userOptional.get().getRole();
        }
        return null; //TODO Correct
    }

    private void setUserAndRoleToSession(HttpServletRequest request,
                                         Role role, String username) {
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("role", role);
    }
}

