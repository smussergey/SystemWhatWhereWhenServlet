package ua.training.game.dao;

import ua.training.game.domain.Question;

import java.util.List;

public interface QuestionDao extends GenericDao<Question> {
    void updateAppealField(Question entity);
    void deleteByGameId(int gameId);
    void update (List<Question> questions);
}
