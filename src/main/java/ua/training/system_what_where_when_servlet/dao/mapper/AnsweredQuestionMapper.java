package ua.training.system_what_where_when_servlet.dao.mapper;

import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AnsweredQuestionMapper implements ObjectMapper<AnsweredQuestion> {
    @Override
    public AnsweredQuestion extractFromResultSet(ResultSet rs) throws SQLException {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion();
        answeredQuestion.setId(rs.getInt("answered_question.answered_question_id"));
        return answeredQuestion;
    }

    @Override
    public AnsweredQuestion makeUnique(Map<Integer, AnsweredQuestion> cache,
                                       AnsweredQuestion answeredQuestion) {
        cache.putIfAbsent(answeredQuestion.getId(), answeredQuestion);
        return cache.get(answeredQuestion.getId());
    }
}
