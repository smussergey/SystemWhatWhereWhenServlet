package ua.training.system_what_where_when_servlet.dao;

import ua.training.system_what_where_when_servlet.entity.Question;

public interface QuestionDao extends GenericDao<Question> {
    void updateAppealField(Question entity);
    void deleteByGameId(int gameId);
}
