package ua.training.system_what_where_when_servlet.controller.command.referee;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.controller.command.player.FileAppealAgainstAnsweredQuestionsPlayerCommand;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


public class ConsiderAppealAgainstAnsweredQuestionsRefereeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FileAppealAgainstAnsweredQuestionsPlayerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("ConsiderAppealAgainstAnsweredQuestionsRefereeCommand class is executing");
        String[] appealedQuestionsId = request.getParameterValues("ids");

        if (appealedQuestionsId.length > 0) {

            int[] appealedAnsweredQuestionIds = Arrays.stream(appealedQuestionsId).mapToInt(Integer::valueOf).toArray();
            LOGGER.info("ConsiderAppealAgainstAnsweredQuestionsRefereeCommand class: parameter ids = " + appealedQuestionsId);

            ServiceFactory.getInstance().getAppealService().approveAppealsAgainstGameAnsweredQuestions(appealedAnsweredQuestionIds);


        }
        return "/WEB-INF/referee/mainReferee.jsp"; // TODO make return to statistics
    }
}