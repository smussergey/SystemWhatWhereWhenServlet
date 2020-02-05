package ua.training.system_what_where_when_servlet.service;


import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Appeal;
import ua.training.system_what_where_when_servlet.entity.AppealStage;
import ua.training.system_what_where_when_servlet.entity.Game;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppealService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private final AnsweredQuestionService answeredQuestionService;
    private final UserService userService;
    private final DaoFactory daoFactory;


    public AppealService() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.answeredQuestionService = serviceFactory.getAnsweredQuestionService();
        this.userService = serviceFactory.getUserService();
        this.daoFactory = DaoFactory.getInstance();
    }


    public void fileAppealAgainstGameAnsweredQuestions(int[] appealedQuestionsIds, String username) {

        LOGGER.info("AppealServise class: fileAppealAgainstGameAnsweredQuestions method: successfully were got in amount:" + appealedQuestionsIds.length);
        List<AnsweredQuestion> appealedQuestions = Arrays.stream(appealedQuestionsIds)
                .mapToObj(answeredQuestionService::findAnsweredQuestionById) // TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        // TODO check if null is impossible
        Game appealedGame = appealedQuestions.stream()
                .findAny()
                .get()
                .getGame();

        LOGGER.info(String.format("in AppealService: fileAppealAgainstGameAnsweredQuestions() - appealedGame: %s successfully was find", appealedGame.getId()));

        Appeal appeal = new Appeal();
        appeal.setDate(LocalDate.now());
        appeal.setUser(userService.findByUsername(username).get()); //TODO check get
        appeal.setGame(appealedGame);
        appeal.getAppealedQuestions().addAll(appealedQuestions);
        appeal.setAppealStage(AppealStage.FILED);
        save(appeal);
    }


    public void save(Appeal appeal) {
        daoFactory.createAppealDao().create(appeal);
    }

    public void approveAppealsAgainstGameAnsweredQuestions(int[] aprovedQuestionsIds) {
        LOGGER.info("AppealServise class: fileAppealAgainstGameAnsweredQuestions method: successfully were got in amount:" + aprovedQuestionsIds.length);
        List<AnsweredQuestion> answeredQuestionsWithApprovedAppeal = Arrays.stream(aprovedQuestionsIds)
                .mapToObj(answeredQuestionService::findAnsweredQuestionById)// TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

//        answeredQuestionService.saveAll(answeredQuestionsWithApprovedAppeal.stream()
//                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
//                .collect(Collectors.toList()));

        List<AnsweredQuestion> approvedAnsweredQuestions = answeredQuestionsWithApprovedAppeal.stream()
                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
                .collect(Collectors.toList());

        approvedAnsweredQuestions.stream() //TODO redo in transaction and batch
                .forEach(aq -> DaoFactory.getInstance().createAnsweredQuestionDao().update(aq));


        Game appealedGame = answeredQuestionsWithApprovedAppeal.stream()
                .findAny()
                .get()
                .getGame();

        // maybe move to separate method and update field through dirty checking

        List<Appeal> consideredAppeals = (appealedGame.getAppeals().stream()
                .peek(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED))
                .collect(Collectors.toList()));


        //TODO redo in transaction and batch
        consideredAppeals.stream()
                .forEach(appeal -> DaoFactory.getInstance().createAppealDao().update(appeal));
    }
}




