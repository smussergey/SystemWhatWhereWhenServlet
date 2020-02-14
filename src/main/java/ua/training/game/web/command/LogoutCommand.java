package ua.training.game.web.command;

import ua.training.game.web.command.util.AuthUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        System.out.println("LogoutCommand username before remove:" + session.getAttribute("username"));
        System.out.println("LogoutCommand role before remove:" + session.getAttribute("role"));

        Set<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");

        loggedUsers.stream().forEach(user -> System.out.println("username: " + user));

        AuthUtil.RemoveUserFromContext(session, username);

        loggedUsers.stream().forEach(user -> System.out.println("username: " + user));

        session.removeAttribute("role");
        session.removeAttribute("username");

        System.out.println("LogoutCommand username after remove:" + session.getAttribute("username"));
        System.out.println("LogoutCommand role after remove:" + session.getAttribute("role"));

        session.invalidate();
        return "/index.jsp";
    }
}

