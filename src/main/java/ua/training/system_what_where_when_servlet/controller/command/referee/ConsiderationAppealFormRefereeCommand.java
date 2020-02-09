//package ua.training.system_what_where_when_servlet.controller.command.referee;
//
//import org.apache.log4j.Logger;
//import ua.training.system_what_where_when_servlet.controller.command.Command;
//import ua.training.system_what_where_when_servlet.dto.GameDTO;
//import ua.training.system_what_where_when_servlet.entity.AppealStage;
//import ua.training.system_what_where_when_servlet.entity.exception.EntityNotFoundException;
//import ua.training.system_what_where_when_servlet.service.GameInformationService;
//import ua.training.system_what_where_when_servlet.service.ServiceFactory;
//import ua.training.system_what_where_when_servlet.util.ResourceBundleUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class ConsiderationAppealFormRefereeCommand implements Command {
//    private static final Logger LOGGER = Logger.getLogger(ConsiderationAppealFormRefereeCommand.class);
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("GetConsiderationAppealFormRefereeCommand class is executing");
//        int gameId = Integer.valueOf(request.getParameter("gameid"));
//        LOGGER.info(String.format("GetConsiderationAppealFormRefereeCommand class: parameter id = %d  was received as request for a game details", gameId));
//        GameInformationService service = ServiceFactory.getInstance().getGameStatisticsAndDetailsService();
//
//        String username = (String) request.getSession().getAttribute("username"); //check casting
//
//        try {
//            GameDTO gameDTO = service.getGameFullStatisticsById(gameId);
//            LOGGER.info(String.format("GetFileAppealForm class: game details were generated for a game with id = %d", gameDTO.getId()));
//            request.setAttribute("gameDTO", gameDTO);
//
//            request.setAttribute("appealStageFiled",
//                    ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
//
//        } catch (EntityNotFoundException ex) {
//            //TODO
//        }
//        return "/WEB-INF/referee/appealConsiderationFormReferee.jsp";
//    }
//}
