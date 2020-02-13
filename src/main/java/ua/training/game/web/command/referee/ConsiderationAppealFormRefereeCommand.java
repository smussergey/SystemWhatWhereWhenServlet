package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.enums.AppealStage;
import ua.training.game.service.AppealService;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.command.Command;
import ua.training.game.web.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConsiderationAppealFormRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ConsiderationAppealFormRefereeCommand.class);
    private final AppealService appealService = new AppealService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Class is executing");
        int gameId = Integer.valueOf(request.getParameter("gameid"));
        LOGGER.info(String.format("parameter id = %d  was received as request for a game details", gameId));
//        GameInformationService service = ServiceFactory.getInstance().getGameStatisticsAndDetailsService();

//        String username = (String) request.getSession().getAttribute("username"); //check casting

        GameDTO gameDTO = appealService.getGameInformationByIdForApprovalToAppealForm(gameId);
        LOGGER.info(String.format("game details were generated for a game with id = %d", gameDTO.getId()));
        request.setAttribute("gameDTO", gameDTO);

        request.setAttribute("appealStageFiled",
                ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));

        return "/WEB-INF/referee/appealConsiderationFormReferee.jsp";
    }

}