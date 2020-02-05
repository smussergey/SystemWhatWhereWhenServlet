package ua.training.system_what_where_when_servlet.controller.filter;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession();

        String path = request.getRequestURI();
        Role role = (Role) session.getAttribute("role");

        boolean isLoggedIn = (session != null &&
                session.getAttribute("username") != null &&
                session.getAttribute("role") != null);
        boolean isLoginRequest = path.contains("login");
        boolean isLogoutRequest = path.contains("logout");
        boolean isError = path.contains("error");

        LOGGER.info("request URI= " + request.getRequestURI());

        if (isError) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!isLoggedIn && isLoginRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (isLoggedIn && isLoginRequest) {
            response.sendRedirect("/logout");
            return;
        }

        if (isLogoutRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isLoggedIn && role.equals(Role.ROLE_REFEREE)) {
            LOGGER.info("in isLoggedIn && role.equals(Role.ROLE_REFEREE): " + (isLoggedIn && role.equals(Role.ROLE_REFEREE)));
            if (isPathAllowedForRole(path, Role.ROLE_REFEREE)) {
                LOGGER.info("in isPathAllowedForRole(path, Role.ROLE_REFEREE): " + isPathAllowedForRole(path, Role.ROLE_REFEREE));
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                LOGGER.warn("in else isPathAllowedForRole(path, Role.ROLE_REFEREE): " + isPathAllowedForRole(path, Role.ROLE_REFEREE));
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

        }
        if (isLoggedIn && role.equals(Role.ROLE_PLAYER)) {
            LOGGER.info("in isLoggedIn && role.equals(Role.ROLE_PLAYER): " + (isLoggedIn && role.equals(Role.ROLE_PLAYER)));
            if (isPathAllowedForRole(path, Role.ROLE_PLAYER)) {
                LOGGER.info("in isPathAllowedForRole(path, Role.ROLE_PLAYER): " + isPathAllowedForRole(path, Role.ROLE_PLAYER));
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                LOGGER.warn("in else isPathAllowedForRole(path, Role.ROLE_PLAYER): " + isPathAllowedForRole(path, Role.ROLE_PLAYER));
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    private boolean isPathAllowedForRole(String path, Role role) {
        LOGGER.info("in isPathAllowedForRole, requested path: " + path + " by user with role " + role.name().toLowerCase());
        return path.contains(role.name().replaceAll("ROLE_", "").toLowerCase());
    }


}