package ua.training.game.web.command.referee;

import org.apache.log4j.Logger;
import ua.training.game.service.AppealService;
import ua.training.game.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


public class ConsiderAppealAgainstAppealedQuestionsRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ConsiderAppealAgainstAppealedQuestionsRefereeCommand.class);
    private final AppealService appealService = new AppealService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("ConsiderAppealAgainstAnsweredQuestionsRefereeCommand class is executing");
        String[] appealedQuestionsId = request.getParameterValues("ids");

        if (appealedQuestionsId.length > 0) {

            int[] appealedAnsweredQuestionIds = Arrays.stream(appealedQuestionsId).mapToInt(Integer::valueOf).toArray();
            LOGGER.info("ConsiderAppealAgainstAnsweredQuestionsRefereeCommand class: parameter ids = " + appealedQuestionsId);

            appealService.approveAppealsAgainstGameAppealedQuestions(appealedAnsweredQuestionIds);

        }
        return "/WEB-INF/referee/homeReferee.jsp"; // TODO make return to statistics
    }
}