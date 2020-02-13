package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.HistoryDao;
import ua.training.game.dao.connection.ConnectionPoolHolder;
import ua.training.game.dao.mapper.HistoryMapper;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.Game;
import ua.training.game.domain.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCHistoryDao implements HistoryDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCHistoryDao.class);

    public JDBCHistoryDao() {
    }

    @Override
    public void create(History entity) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }


    @Override
    public void createAndDeleteGameRecord(History history, Game game) {
        LOGGER.info(String.format("In method createAndDeleteGameRecord"));

        try (Connection connection = ConnectionPoolHolder.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psInsertHistory = connection.prepareStatement
                    ("INSERT INTO history (date, first_player_name_en, first_player_name_ua, " +
                            "second_player_name_en, second_player_name_ua, scores, appeal_stage )" +
                            " VALUES (?,?,?,?,?,?,?)");
                 PreparedStatement psDeleteAppealedQuestion = connection.prepareStatement
                         ("DELETE  FROM appealed_question where appeal_id = ?");
                 PreparedStatement psDeleteQuestion = connection.prepareStatement
                         ("DELETE  FROM question where game_id = ?");
                 PreparedStatement psDeleteAppeal = connection.prepareStatement
                         ("DELETE  FROM appeal where game_id = ?");
                 PreparedStatement psDeleteGame = connection.prepareStatement
                         ("DELETE  FROM game where game_id = ?")) {

                psInsertHistory.setDate(1, Date.valueOf(history.getDate()));
                psInsertHistory.setString(2, history.getFirstPlayerNameEn());
                psInsertHistory.setString(3, history.getFirstPlayerNameUa());
                psInsertHistory.setString(4, history.getSecondPlayerNameEn());
                psInsertHistory.setString(5, history.getSecondPlayerNameUa());
                psInsertHistory.setString(6, history.getScores());
                psInsertHistory.setString(7, history.getAppealStage());
                psInsertHistory.executeUpdate();

                for (Appeal appeal : game.getAppeals()) {
                    psDeleteAppealedQuestion.setInt(1, appeal.getId());
                    psDeleteAppealedQuestion.executeUpdate();
                }

                psDeleteQuestion.setInt(1, game.getId());
                psDeleteQuestion.executeUpdate();

                psDeleteAppeal.setInt(1, game.getId());
                psDeleteAppeal.executeUpdate();

                psDeleteGame.setInt(1, game.getId());
                psDeleteGame.executeUpdate();

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
        LOGGER.info("History was saved");
    }

    @Override
    public Optional<History> findById(int id) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public List<History> findAll() {
        List<History> histories = new ArrayList<>();
        final String query = " select * from history order by date desc";

        try (Connection connection = ConnectionPoolHolder.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            HistoryMapper historyMapper = new HistoryMapper();

            while (rs.next()) {
                History history = historyMapper.extractFromResultSet(rs);
                histories.add(history);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
        return histories;
    }


    @Override
    public void update(History entity) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

}