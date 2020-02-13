package ua.training.game.web.command.player;

import org.apache.log4j.Logger;
import ua.training.game.service.AppealService;
import ua.training.game.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class FileAppealAgainstAnsweredQuestionsPlayerCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FileAppealAgainstAnsweredQuestionsPlayerCommand.class);
    AppealService appealService = new AppealService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("FileAppealAgainstAnsweredQuestionsPlayerCommand class is executing");
        String[] appealedQuestionsId = request.getParameterValues("ids");

        if (appealedQuestionsId.length > 0) {  //TODO improve

            int[] appealedAnsweredQuestionIds = Arrays.stream(appealedQuestionsId).mapToInt(Integer::valueOf).toArray();LOGGER.info("FileAppealAgainstAnsweredQuestionsPlayerCommand class: parameter ids = " + appealedQuestionsId);

            String username = (String) request.getSession().getAttribute("username"); //check casting
            appealService.fileAppealAgainstGameQuestions(appealedAnsweredQuestionIds, username);


        }
        return "/WEB-INF/player/homePlayer.jsp"; // TODO make return to statistics
    }
}