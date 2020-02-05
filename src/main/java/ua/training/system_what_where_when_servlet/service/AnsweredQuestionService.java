package ua.training.system_what_where_when_servlet.service;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.dto.AnsweredQuestionDTO;
import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.AppealStage;
import ua.training.system_what_where_when_servlet.util.ResourceBundleUtil;

import java.sql.SQLException;
import java.util.List;

public class AnsweredQuestionService {
    private static final Logger LOGGER = Logger.getLogger(AnsweredQuestionService.class);
    private final DaoFactory daoFactory;
    private final ServiceFactory serviceFactory;
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

    public AnsweredQuestionService() {
        this.daoFactory = DaoFactory.getInstance();
        this.serviceFactory = ServiceFactory.getInstance();
    }

    public AnsweredQuestion findAnsweredQuestionById(int id) {
        try {
            return DaoFactory.getInstance().createAnsweredQuestionDao().findById(id).get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null; // TODO correct
    }


    public AnsweredQuestionDTO toAnsweredQuestionDTO(AnsweredQuestion answeredQuestion) {
        LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method is executing on a answeredQuestionDTO with id = %d", answeredQuestion.getId()));
        AnsweredQuestionDTO answeredQuestionDTO = new AnsweredQuestionDTO();
        answeredQuestionDTO.setId(answeredQuestion.getId());

        if (answeredQuestion.getUserWhoGotPoint() != null) {
            LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method, getUserWhoGotPoint = %s", answeredQuestion.getUserWhoGotPoint()));
            answeredQuestionDTO.setNameWhoGotPointUa(answeredQuestion.getUserWhoGotPoint().getNameUa());
            answeredQuestionDTO.setNameWhoGotPointEn(answeredQuestion.getUserWhoGotPoint().getNameEn());
        } else {
            answeredQuestionDTO.setNameWhoGotPointUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
            LOGGER.info(String.format("AnsweredQuestionService class, toAnsweredQuestionDTO method, else getUserWhoGotPoint = %s", ResourceBundleUtil.getBundleString("games.game.statistics.text.audience")));
            answeredQuestionDTO.setNameWhoGotPointEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
        }

        if (answeredQuestion.getAppeal() != null) {
            answeredQuestionDTO.setAppealStage(
                    ResourceBundleUtil.getBundleStringForAppealStage(
                            answeredQuestion.getAppeal().getAppealStage().name()));

        } else answeredQuestionDTO.setAppealStage(
                ResourceBundleUtil.getBundleStringForAppealStage(
                        AppealStage.NOT_FILED.name()));


        return answeredQuestionDTO;
    }

//    public List<AnsweredQuestion> saveAll(List<AnsweredQuestion> answeredQuestions) {
//        return answeredQuestionRepository.saveAll(answeredQuestions);
//    }
}
