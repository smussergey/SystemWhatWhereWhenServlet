//package ua.training.system_what_where_when_servlet.controller.command.referee;
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.controller.command.Command;
//import ua.training.system_what_where_when_servlet.dto.GameDTO;
//import ua.training.system_what_where_when_servlet.entity.History;
//import ua.training.system_what_where_when_servlet.service.HistoryService;
//import ua.training.system_what_where_when_servlet.service.ServiceFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//public class HistoryConsiderationRefereeCommand implements Command {
//    private static final Logger LOGGER = Logger.getLogger(HistoryConsiderationRefereeCommand.class);
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("class is executing");
//
//        HistoryService service = ServiceFactory.getInstance().getHistoryService();
//        List<GameDTO> gameDTOs = service.getGamesWhichCanBeMovedToHistory();
//        request.setAttribute("gameDTOs", gameDTOs);
//        LOGGER.info(String.format("gameDTOs were generated in amount = %d", gameDTOs.size()));
//
//
//        return "/WEB-INF/referee/historyConsiderationReferee.jsp";
//    }
//}
