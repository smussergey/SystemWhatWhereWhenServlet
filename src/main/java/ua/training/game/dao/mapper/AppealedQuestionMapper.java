package ua.training.game.dao.mapper;

import ua.training.game.domain.AppealedQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AppealedQuestionMapper implements ObjectMapper<AppealedQuestion> {
    @Override
    public AppealedQuestion extractFromResultSet(ResultSet rs) throws SQLException {
        AppealedQuestion appealedQuestion = new AppealedQuestion();
        appealedQuestion.setId(rs.getInt("appealed_question_id"));
        return appealedQuestion;
    }

    @Override
    public AppealedQuestion makeUnique(Map<Integer, AppealedQuestion> cache, AppealedQuestion appealedQuestion) {
        cache.putIfAbsent(appealedQuestion.getId(), appealedQuestion);
        return cache.get(appealedQuestion.getId());
    }
}
