package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.AppealDao;
import ua.training.game.dao.connection.ConnectionPoolHolder;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.AppealedQuestion;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCAppealDao implements AppealDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCAppealDao.class);

    public JDBCAppealDao() {
    }

    @Override
    public void create(Appeal appeal) {
        LOGGER.info(String.format("In AppealDaoImpl, method create appeal: "));
        ResultSet rs;
        int appealId;

        try (Connection connection = ConnectionPoolHolder.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psInsertAppeal = connection.prepareStatement
                    ("INSERT INTO appeal (date, game_id, user_id, appeal_stage)" +
                            " VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psInsertAppealedQuestions = connection.prepareStatement
                         ("INSERT  INTO appealed_question (appeal_id, question_id) VALUES (?,?)")) {

                psInsertAppeal.setDate(1, Date.valueOf(appeal.getDate()));
                psInsertAppeal.setInt(2, appeal.getGame().getId());
                psInsertAppeal.setInt(3, appeal.getUser().getId());
                psInsertAppeal.setString(4, appeal.getAppealStage().name());

                int rowAffected = psInsertAppeal.executeUpdate();

                LOGGER.info(String.format("In AppealDaoImpl, method create, after appeal insert: " + appeal));


                if (rowAffected == 0) {
                    throw new SQLException("Creating Appeal failed, no rows affected.");
                }
                rs = psInsertAppeal.getGeneratedKeys();

                if (rs.next()) {
                    appealId = rs.getInt(1);

                    List<AppealedQuestion> appealedQuestions = appeal.getAppealedQuestions();
                    for (AppealedQuestion appealedQuestion : appealedQuestions) { //TODO improve
                        LOGGER.info(String.format("In AppealDaoImpl, method create appeal, aq size = %d", appealedQuestions.size()));
                        psInsertAppealedQuestions.setInt(1, appealId);
                        psInsertAppealedQuestions.setInt(2, appealedQuestion.getQuestion().getId());
                        psInsertAppealedQuestions.executeUpdate();
                    }
                }

                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                LOGGER.error(e.getMessage());
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Optional<Appeal> findById(int id) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public List<Appeal> findAll() {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public void update(Appeal appeal) {
        LOGGER.info(String.format("In AppealDaoImpl, method update appeal: "));

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("UPDATE  appeal set appeal_stage = ? where appeal_id = ?")) {

            ps.setString(1, appeal.getAppealStage().name());
            ps.setInt(2, appeal.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
    }

    @Override
    public void update(List<Appeal> appeals) {
        LOGGER.info(String.format("In AppealDaoImpl, method update appeal: "));

        try (Connection connection = ConnectionPoolHolder.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psUpdate = connection.prepareStatement
                    ("UPDATE  appeal set appeal_stage = ? where appeal_id = ?")) {

                for (int i = 0; i < appeals.size(); i++) {
                    psUpdate.setString(1, appeals.get(i).getAppealStage().name());
                    psUpdate.setInt(2, appeals.get(i).getId());
                    psUpdate.executeUpdate();
                }

                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                LOGGER.error(e.getMessage());
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public void deleteByGameId(int gameId) {
        throw new UnsupportedOperationException("Method is not implemented yet"); //TODO check if it is needed
    }
}
