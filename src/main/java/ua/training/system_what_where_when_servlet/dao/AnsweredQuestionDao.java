package ua.training.system_what_where_when_servlet.dao;

import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Game;

import java.util.List;

public interface AnsweredQuestionDao extends GenericDao<AnsweredQuestion> {
    void updateAppealField(AnsweredQuestion entity);
    void deleteByGameId(int gameId);
}
