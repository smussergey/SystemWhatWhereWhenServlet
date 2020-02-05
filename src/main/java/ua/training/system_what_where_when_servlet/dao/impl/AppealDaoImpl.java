package ua.training.system_what_where_when_servlet.dao.impl;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.AppealDao;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Appeal;
import ua.training.system_what_where_when_servlet.entity.exception.NotUniqueLoginException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AppealDaoImpl implements AppealDao {
    private static final Logger LOGGER = Logger.getLogger(AppealDaoImpl.class);
    private Connection connection;

    public AppealDaoImpl(Connection connection) {
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
                    ("INSERT INTO appeal (appeal_stage, date, game_id, user_id)" +
                            " VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psAQ = connection.prepareStatement
                         ("UPDATE  answered_question set appeal_id = ? where answered_question_id = ?")
            ) {
                psAppeal.setString(1, appeal.getAppealStage().name());
                psAppeal.setDate(2, Date.valueOf(appeal.getDate()));
                psAppeal.setInt(3, appeal.getGame().getId());
                psAppeal.setInt(4, appeal.getUser().getId());

                int rowAffected = psAppeal.executeUpdate();

                LOGGER.info(String.format("In AppealDaoImpl, method create, after appeal insert: " + appeal));


                if (rowAffected == 0) {
                    throw new SQLException("Creating Appeal failed, no rows affected.");
                }
                rs = psAppeal.getGeneratedKeys();

                if (rs.next()) {
                    appealId = rs.getInt(1);

                    List<AnsweredQuestion> appealedQuestions = appeal.getAppealedQuestions();
                    for (int i = 0; i < appealedQuestions.size(); i++) { //TODO improve
                        LOGGER.info(String.format("In AppealDaoImpl, method create appeal, aq size = %d", appealedQuestions.size()));
                        LOGGER.info(String.format("In AppealDaoImpl, method create appeal, for i(aq) = %d", i));
                        AnsweredQuestion appealedQuestion = appealedQuestions.get(i);
                        psAQ.setInt(1, appealId);
                        psAQ.setInt(2, appealedQuestion.getId());
                        psAQ.executeUpdate();
                    }
                }
//            flag = true;
                connection.commit();
                LOGGER.info("Appeal was saved");
            }
            connection.rollback(); // TODO check where to put it correctly
        } catch (
                SQLException ex) {

//TODO implement
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Appeal> findById(int id) throws SQLException {
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
