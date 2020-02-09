//package ua.training.system_what_where_when_servlet.controller.command.player;
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.controller.command.Command;
//import ua.training.system_what_where_when_servlet.dto.GameDTO;
//import ua.training.system_what_where_when_servlet.entity.exception.EntityNotFoundException;
//import ua.training.system_what_where_when_servlet.service.GameInformationService;
//import ua.training.system_what_where_when_servlet.service.ServiceFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class GameDetailsPlayerCommand implements Command {
//    private static final Logger LOGGER = Logger.getLogger(GameDetailsPlayerCommand.class);
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("GameDetailsPlayerCommand class is executing");
//        int gameId = Integer.valueOf(request.getParameter("gameid"));
//        LOGGER.info(String.format("GameDetailsPlayerCommand class: parameter id = %d  was received as request for a game details", gameId));
//        GameInformationService service = ServiceFactory.getInstance().getGameStatisticsAndDetailsService();
//
//        String username = (String) request.getSession().getAttribute("username"); //check casting
//
//        try {
//            GameDTO gameDTO = service.getGameFullStatisticsByIdAndUsername(gameId, username);
//            LOGGER.info(String.format("GameDetailsPlayerCommand class: game details were generated for a game with id = %d", gameDTO.getId()));
//            request.setAttribute("gameDTO", gameDTO);
//        } catch (EntityNotFoundException ex) {
//            //TODO
//        }
//        return "/WEB-INF/player/gameDetailsPlayer.jsp";
//    }
//}
