package ua.training.game.service;

import org.apache.log4j.Logger;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.AppealedQuestion;
import ua.training.game.domain.Question;

//
public class AppealedQuestionService {
    private static final Logger LOGGER = Logger.getLogger(AppealedQuestionService.class);

    public AppealedQuestion toAppealedQuestion(Question question, Appeal appeal) {
        AppealedQuestion appealedQuestion = new AppealedQuestion();
        appealedQuestion.setQuestion(question);
        appealedQuestion.setAppeal(appeal);
        return appealedQuestion;
    }
}