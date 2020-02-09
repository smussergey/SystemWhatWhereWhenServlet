package ua.training.system_what_where_when_servlet.dao.mapper;

import ua.training.system_what_where_when_servlet.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class QuestionMapper implements ObjectMapper<Question> {
    @Override
    public Question extractFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("question.question_id"));
        return question;
    }

    @Override
    public Question makeUnique(Map<Integer, Question> cache,
                               Question question) {
        cache.putIfAbsent(question.getId(), question);
        return cache.get(question.getId());
    }
}
