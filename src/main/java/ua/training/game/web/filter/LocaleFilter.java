package ua.training.game.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        String localeName = servletRequest.getParameter("lang");
        if (localeName != null) {
            httpReq.getSession().setAttribute("lang", localeName);
        }
        filterChain.doFilter(httpReq, httpResp);
    }

    @Override
    public void destroy() {

    }
}
