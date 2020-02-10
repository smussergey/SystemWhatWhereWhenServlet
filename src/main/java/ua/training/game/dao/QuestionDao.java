package ua.training.game.dao;

import ua.training.game.domain.Question;

public interface QuestionDao extends GenericDao<Question> {
    void updateAppealField(Question entity);
    void deleteByGameId(int gameId);
}
