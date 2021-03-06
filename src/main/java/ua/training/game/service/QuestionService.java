package ua.training.game.service;

import org.apache.log4j.Logger;
import ua.training.game.dao.DaoFactory;
import ua.training.game.domain.Game;
import ua.training.game.domain.Question;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.QuestionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionService {
    private static final Logger LOGGER = Logger.getLogger(QuestionService.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();



//    private final QuestionRepository questionRepository;
//
//    public QuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }


//    public Question findById(Long id) {
//        return questionRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Can not fond question with id: " + id));
//    }

    public Question findById(Integer id) { //TODO check to use int or Integer everywhere
        return daoFactory.createQuestionDao().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not fond question with id: " + id));
    }

    public List<QuestionDTO> extractQuestionDTOsFromGame(Game game) {
        return game.getQuestions().stream()
                .map(question -> toQuestionDTO(question, game))
                .collect(Collectors.toList());
    }


    private QuestionDTO toQuestionDTO(Question question, Game game) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setNameWhoGotPointUa(question.getUserWhoGotPoint().getNameUa());
        questionDTO.setNameWhoGotPointEn(question.getUserWhoGotPoint().getNameEn());
        setDefaultAppealStageForQuestionDTO(questionDTO);
        changeAppealStageForQuestionDTOIfAppealExists(question, questionDTO, game);

        return questionDTO;
    }

    private void setDefaultAppealStageForQuestionDTO(QuestionDTO questionDTO) {
        questionDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
    }

    private void changeAppealStageForQuestionDTOIfAppealExists(Question question, QuestionDTO questionDTO, Game game) {
        game.getAppeals()
                .forEach(appeal -> appeal.getAppealedQuestions().stream()
                        .filter(appealedQuestion -> appealedQuestion.getQuestion().equals(question))
                        .findAny()
                        .ifPresent(appealedQuestion -> questionDTO.setAppealStage(ResourceBundleUtil
                                .getBundleStringForAppealStage(appealedQuestion
                                        .getAppeal()
                                        .getAppealStage()
                                        .name()))));
    }


    public void update(List<Question> questions) {
       DaoFactory.getInstance().createQuestionDao().update(questions);
    }
}



//
//
//
//
//
//
//    private final AnsweredQuestionRepository answeredQuestionRepository;
//
//    public AnsweredQuestionService(AnsweredQuestionRepository answeredQuestionRepository) {
//        this.answeredQuestionRepository = answeredQuestionRepository;
//    }
//
//    //TODO refactor this method
//    public AnsweredQuestion findAnsweredQuestionById(Long id) {
//        return answeredQuestionRepository.findById(id).get();
//    }
//
//    public QuestionService() {
//        this.daoFactory = DaoFactory.getInstance();
//        this.serviceFactory = ServiceFactory.getInstance();
//    }
//
//    public Question findAnsweredQuestionById(int id) {
//        try {
//            return DaoFactory.getInstance().createAnsweredQuestionDao().findById(id).get();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    return null; // TODO correct
//    }
//
//
//    public QuestionDTO toAnsweredQuestionDTO(Question question) {
//        LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method is executing on a answeredQuestionDTO with id = %d", question.getId()));
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setId(question.getId());
//
//        if (question.getUserWhoGotPoint() != null) {
//            LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method, getUserWhoGotPoint = %s", question.getUserWhoGotPoint()));
//            questionDTO.setNameWhoGotPointUa(question.getUserWhoGotPoint().getNameUa());
//            questionDTO.setNameWhoGotPointEn(question.getUserWhoGotPoint().getNameEn());
//        } else {
//            questionDTO.setNameWhoGotPointUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//            LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method, else getUserWhoGotPoint = %s", ResourceBundleUtil.getBundleString("games.game.statistics.text.audience")));
//            questionDTO.setNameWhoGotPointEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//        }
//
//        if (question.getAppeal() != null) {
//            questionDTO.setAppealStage(
//                    ResourceBundleUtil.getBundleStringForAppealStage(
//                            question.getAppeal().getAppealStage().name()));
//
//        } else questionDTO.setAppealStage(
//                ResourceBundleUtil.getBundleStringForAppealStage(
//                        AppealStage.NOT_FILED.name()));
//
//
//        return questionDTO;
//    }
//
//    public List<AnsweredQuestion> saveAll(List<AnsweredQuestion> answeredQuestions) {
//        return answeredQuestionRepository.saveAll(answeredQuestions);
//    }
//}
