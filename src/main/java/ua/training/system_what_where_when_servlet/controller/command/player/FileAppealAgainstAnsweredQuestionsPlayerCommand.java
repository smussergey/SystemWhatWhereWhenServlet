package ua.training.system_what_where_when_servlet.controller.command.player;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.controller.command.Command;
import ua.training.system_what_where_when_servlet.controller.command.LoginCommand;
import ua.training.system_what_where_when_servlet.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FileAppealAgainstAnsweredQuestionsPlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FileAppealAgainstAnsweredQuestionsPlayerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("FileAppealAgainstAnsweredQuestionsPlayerCommand class is executing");
        String[] appealedQuestionsId = request.getParameterValues("ids");

        if (appealedQuestionsId.length > 0) {

            int[] appealedAnsweredQuestionIds = Arrays.stream(appealedQuestionsId).mapToInt(Integer::valueOf).toArray();LOGGER.info("FileAppealAgainstAnsweredQuestionsPlayerCommand class: parameter ids = " + appealedQuestionsId);

            String username = (String) request.getSession().getAttribute("username"); //check casting
            ServiceFactory.getInstance().getAppealService().fileAppealAgainstGameAnsweredQuestions(appealedAnsweredQuestionIds, username);


        }
        return "/WEB-INF/player/mainPlayer.jsp"; // TODO make return to statistics
    }
}