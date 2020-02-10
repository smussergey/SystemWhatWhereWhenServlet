package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.AppealDao;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.AppealedQuestion;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCAppealDao implements AppealDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCAppealDao.class);
    private Connection connection;

    public JDBCAppealDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Appeal appeal) {
        LOGGER.info(String.format("In AppealDaoImpl, method create appeal: "));
        ResultSet rs;
        int appealId;

        try {
            connection.setAutoCommit(false);

//        boolean flag = false;
            try (PreparedStatement psAppeal = connection.prepareStatement
                    ("INSERT INTO appeal (date, game_id, user_id, appeal_stage)" +
                            " VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psAQ = connection.prepareStatement
                         ("INSERT  INTO appealed_question (appeal_id, question_id) VALUES (?,?)")
            ) {
                psAppeal.setDate(1, Date.valueOf(appeal.getDate()));
                psAppeal.setInt(2, appeal.getGame().getId());
                psAppeal.setInt(3, appeal.getUser().getId());
                psAppeal.setString(4, appeal.getAppealStage().name());

                int rowAffected = psAppeal.executeUpdate();

                LOGGER.info(String.format("In AppealDaoImpl, method create, after appeal insert: " + appeal));


                if (rowAffected == 0) {
                    throw new SQLException("Creating Appeal failed, no rows affected.");
                }
                rs = psAppeal.getGeneratedKeys();

                if (rs.next()) {
                    appealId = rs.getInt(1);

                    List<AppealedQuestion> appealedQuestions = appeal.getAppealedQuestions();
                    for (AppealedQuestion appealedQuestion : appealedQuestions) { //TODO improve
                        LOGGER.info(String.format("In AppealDaoImpl, method create appeal, aq size = %d", appealedQuestions.size()));
                        psAQ.setInt(1, appealId);
                        psAQ.setInt(2, appealedQuestion.getQuestion().getId());
                        psAQ.executeUpdate();
                    }
                }

                connection.commit();
                LOGGER.info("Appeal was saved");
            }

        } catch (
                SQLException ex) {
            try {
                connection.rollback(); // TODO check where to put it correctly
            } catch (SQLException e) {
                e.printStackTrace();//TODO implement
            }
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Appeal> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Appeal> findAll() {
        return null;
    }

    @Override
    public void update(Appeal appeal) {
        LOGGER.info(String.format("In AppealDaoImpl, method update appeal: "));

        try (PreparedStatement ps = connection.prepareStatement
                ("UPDATE  appeal set appeal_stage = ? where appeal_id = ?")) {

            ps.setString(1, appeal.getAppealStage().name());
            ps.setInt(2, appeal.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO REDO
        }
    }


    @Override
    public void delete(int id) {
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
