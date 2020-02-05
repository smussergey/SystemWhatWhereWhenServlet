package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.entity.History;
import ua.training.system_what_where_when_servlet.service.HistoryService;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HistoryGamesStatisticsRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryGamesStatisticsRefereeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("class is executing");

        HistoryService service = ServiceFactory.getInstance().getHistoryService();
        List<History> histories = service.findAll();
        request.setAttribute("histories", histories);
        LOGGER.info(String.format("histories were generated in amount = %d", histories.size()));

        return "/WEB-INF/referee/historyGamesStatisticsReferee.jsp";
    }
}
