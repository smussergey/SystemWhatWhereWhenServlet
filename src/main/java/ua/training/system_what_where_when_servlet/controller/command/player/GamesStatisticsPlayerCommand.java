//package ua.training.system_what_where_when_servlet.controller.command.player;
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.controller.command.Command;
//import ua.training.system_what_where_when_servlet.dto.GameDTO;
//import ua.training.system_what_where_when_servlet.service.GameInformationService;
//import ua.training.system_what_where_when_servlet.service.ServiceFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//public class GamesStatisticsPlayerCommand implements Command {
//    private static final Logger LOGGER = Logger.getLogger(GamesStatisticsPlayerCommand.class);
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("GamesStatisticsPlayerCommand class is executing");
//        GameInformationService service = ServiceFactory.getInstance().getGameStatisticsAndDetailsService();
//
//        String username = (String) request.getSession().getAttribute("username"); //check casting
//
//        List<GameDTO> gameDTOs = service.getGamesStatisticsByLoggedInPlayer(username);
//        request.setAttribute("gameDTOs", gameDTOs);
//        LOGGER.info(String.format("GamesStatisticsPlayerCommand class: GameDTO were generated in amount = %d", gameDTOs.size()));
//        return "/WEB-INF/player/gamesStatisticsPlayer.jsp";
//    }
//}