package ua.training.system_what_where_when_servlet.dao.impl;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.AnsweredQuestionDao;
import ua.training.system_what_where_when_servlet.dao.mapper.AnsweredQuestionMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.AppealMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.GameMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.UserMapper;
import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Appeal;
import ua.training.system_what_where_when_servlet.entity.Game;
import ua.training.system_what_where_when_servlet.entity.User;
import ua.training.system_what_where_when_servlet.entity.exception.EntityNotFoundException;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnsweredQuestionDaoImpl implements AnsweredQuestionDao {
    private static final Logger LOGGER = Logger.getLogger(AnsweredQuestionDaoImpl.class);
    private Connection connection;

    public AnsweredQuestionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(AnsweredQuestion entity) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public Optional<AnsweredQuestion> findById(int id) throws SQLException {
        LOGGER.info(String.format("In AnsweredQuestionDaoImpl, method findById: " + id));
        Optional<AnsweredQuestion> result = Optional.empty();
//        try (PreparedStatement ps = connection.prepareStatement("" +
//                " select * from answered_question " +
//                " left join game " +
//                " on answered_question.game_id = game.game_id " +
//                " left join user " +
//                " on answered_question.user_id=user.user_id " +
//                " left join appeal " +
//                " on answered_question.appeal_id = appeal.appeal_id " +
//                " where answered_question.answered_question_id = ?")) {

        try (PreparedStatement ps = connection.prepareStatement("" +
                " select * from answered_question " +
                " left join game " +
                " on answered_question.game_id = game.game_id " +
                " left join user " +
                " on answered_question.user_id=user.user_id " +
                " left join appeal " +
                " on answered_question.appeal_id = appeal.appeal_id " +
                " left join user as userappealed " +
                " on appeal.user_id =userappealed.user_id " +
                " where answered_question.answered_question_id = ?")) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();


            while (rs.next()) {
                AnsweredQuestion answeredQuestion = // TODO check what to do if ID doesn't exist
                        answeredQuestionMapper.extractFromResultSet(rs);

                Game game = gameMapper
                        .extractFromResultSet(rs);

                answeredQuestion.setGame(game);

                if (rs.getInt("answered_question.user_id") > 0) {
                    User user = userMapper
                            .extractFromResultSet(rs);
                    answeredQuestion.setUserWhoGotPoint(user);
                }


                if (rs.getInt("answered_question.appeal_id") > 0) {
                    Appeal appeal = appealMapper
                            .extractFromResultSet(rs);
                    User userWhoFiledAppeal = userMapper
                            .extractFromResultSetForAppeal(rs);
                    appeal.setUser(userWhoFiledAppeal);
                    game.getAppeals().add(appeal);
                    answeredQuestion.setAppeal(appeal);
                }

                result = Optional.of(answeredQuestion);
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception in class: AnsweredQuestionDaoImpl, method: findById.", ex);
            throw new EntityNotFoundException("not found"); // TODO correct
        }
        LOGGER.info(String.format("In AnsweredQuestionDaoImpl, method findById entity was found with id = " + id));
        return result;

    }

    @Override
    public List<AnsweredQuestion> findAll() {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public void update(AnsweredQuestion answeredQuestion) {
        LOGGER.info(String.format("In AnsweredQuestionDaoImpl, method update answeredQuestion: "));

        try (PreparedStatement psAQ = connection.prepareStatement
                ("UPDATE  answered_question set user_id = ? where answered_question_id = ?")) {

            psAQ.setInt(1, answeredQuestion.getUserWhoGotPoint().getId());
            psAQ.setInt(2, answeredQuestion.getId());
            psAQ.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAppealField(AnsweredQuestion answeredQuestion) { // TODO in transaction when appeal is saved
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
        LOGGER.info(String.format("method deleteByGameId id = %d", gameId));

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
