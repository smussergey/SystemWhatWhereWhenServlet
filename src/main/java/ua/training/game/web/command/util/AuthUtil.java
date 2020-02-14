package ua.training.game.web.command.util;

import org.apache.log4j.Logger;
import ua.training.game.enums.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class AuthUtil {
    private static final Logger LOGGER = Logger.getLogger(AuthUtil.class);

    public static void addUserAndRoleToSession(HttpServletRequest request, Role role, String username) {
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("role", role);
        LOGGER.info(String.format("User with username: %s and role: %s was added to session", username, role.name()));
    }

    public static void addUserToContext(HttpServletRequest request, String username) {
        ServletContext context = request.getSession().getServletContext();
        Set<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        loggedUsers.add(username);
        context.setAttribute("loggedUsers", loggedUsers);
        LOGGER.info(String.format("User %s was added to context loggedUsers", username));
    }


    public static void RemoveUserFromContext( HttpSession session, String username) {
        ServletContext context = session.getServletContext();
        Set<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        loggedUsers.remove(username);
        context.setAttribute("loggedUsers", loggedUsers);
        LOGGER.info(String.format("User %s was removed to context loggedUsers", username));
    }
}
