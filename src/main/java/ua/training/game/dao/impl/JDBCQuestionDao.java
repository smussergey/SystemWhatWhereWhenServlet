package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.QuestionDao;
import ua.training.game.dao.mapper.GameMapper;
import ua.training.game.dao.mapper.QuestionMapper;
import ua.training.game.dao.mapper.UserMapper;
import ua.training.game.domain.Game;
import ua.training.game.domain.Question;
import ua.training.game.domain.User;
import ua.training.game.exception.EntityNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCQuestionDao implements QuestionDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCQuestionDao.class);
    private Connection connection;

    public JDBCQuestionDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Question entity) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public Optional<Question> findById(int id) {
        LOGGER.info(String.format("In method findById: " + id));
        Optional<Question> result = Optional.empty();

        try (PreparedStatement ps = connection.prepareStatement("" +
                " select * from question " +
                " left join game " +
                " on question.game_id = game.game_id " +
                " left join user " +
                " on question.user_id=user.user_id " +
                " where question.question_id = ?")) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            QuestionMapper answeredQuestionMapper = new QuestionMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();


            while (rs.next()) {
                Question question = // TODO check what to do if ID doesn't exist
                        answeredQuestionMapper.extractFromResultSet(rs);

                Game game = gameMapper
                        .extractFromResultSet(rs);
                question.setGame(game);

                User user = userMapper
                        .extractFromResultSet(rs);
                question.setUserWhoGotPoint(user);

                result = Optional.of(question);
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception in method: findById.", ex);
            throw new EntityNotFoundException("not found"); // TODO correct
        }
        LOGGER.info(String.format("In method findById entity was found with id = " + id));

        return result;

    }

    @Override
    public List<Question> findAll() {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public void update(Question question) {
        LOGGER.info(String.format("In method update answeredQuestion: "));

        try (PreparedStatement psAQ = connection.prepareStatement
                ("UPDATE  answered_question set user_id = ? where answered_question_id = ?")) {

            psAQ.setInt(1, question.getUserWhoGotPoint().getId());
            psAQ.setInt(2, question.getId());
            psAQ.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAppealField(Question question) { // TODO in transaction when appeal is saved
//        LOGGER.info(String.format("In AnsweredQuestionDaoImpl, method updateAppealField: " + answeredQuestion.getId()));
//
//        {
//            LOGGER.info(String.format("In AppealDaoImpl, method create appeal: "));
////        boolean flag = false;
//            try (PreparedStatement ps = connection.prepareStatement
//                    ("UPDATE  answered_question set appeal_id = ? where answered_question.id = ?")) {
//                ps.setInt(1, answeredQuestion.getAppeal().getId());
//                ps.setInt(2, answeredQuestion.getId());
//                ps.executeUpdate();
////            flag = true;
//            } catch (Exception ex) { //TODO check what exception to use
//                LOGGER.error("SQLException: " + ex.toString());
//                ex.printStackTrace();
//                return;
//            }
//            LOGGER.info("AnsweredQuestion was saved");
////    return flag;
//        }
//
//
//
//
//

    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Method is not implemented");
    }


    @Override
    public void deleteByGameId(int gameId) {
        LOGGER.info(String.format("In method deleteByGameId id = %d", gameId));

        try (PreparedStatement ps = connection.prepareStatement
                ("DELETE  FROM appeal where game_id = ?")) {

            ps.setInt(1, gameId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO redo
        }
    }


    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO correct
        }
    }


}
