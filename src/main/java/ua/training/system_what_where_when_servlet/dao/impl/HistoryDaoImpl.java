package ua.training.system_what_where_when_servlet.dao.impl;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.HistoryDao;
import ua.training.system_what_where_when_servlet.dao.mapper.*;
import ua.training.system_what_where_when_servlet.entity.*;
import ua.training.system_what_where_when_servlet.entity.exception.NotUniqueLoginException;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class HistoryDaoImpl implements HistoryDao {
    private static final Logger LOGGER = Logger.getLogger(HistoryDaoImpl.class);
    private Connection connection;

    public HistoryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(History entity) {

    }

    @Override
    public void createAndDeleteGameRecords(History history, Game game) {
        LOGGER.info(String.format("method create history"));
//        boolean flag = false;

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement psCreateHistory = connection.prepareStatement
                    ("INSERT INTO history (date, player_name_en, player_name_ua, " +
                            "opponent_name_en, opponent_name_ua, scores, appeal_stage )" +
                            " VALUES (?,?,?,?,?,?,?)");
                 PreparedStatement psDeleteGame = connection.prepareStatement
                         ("DELETE  FROM game where game_id = ?");
                 PreparedStatement psDeleteUser_Game = connection.prepareStatement
                         ("DELETE  FROM user_game where game_id = ?");
                 PreparedStatement psDeleteAppeal = connection.prepareStatement
                         ("DELETE  FROM appeal where game_id = ?");
                 PreparedStatement psDeleteAnsweredQuestion = connection.prepareStatement
                         ("DELETE  FROM answered_question where game_id = ?")) {

                psCreateHistory.setDate(1, Date.valueOf(history.getDate()));
                psCreateHistory.setString(2, history.getPlayerNameEn());
                psCreateHistory.setString(3, history.getOpponentNameUa());
                psCreateHistory.setString(4, history.getOpponentNameEn());
                psCreateHistory.setString(5, history.getOpponentNameUa());
                psCreateHistory.setString(6, history.getScores());
                psCreateHistory.setString(7, history.getAppealStage());

                psDeleteUser_Game.setInt(1, game.getId());

                psDeleteGame.setInt(1, game.getId());

                psDeleteAppeal.setInt(1, game.getId());

                psDeleteAnsweredQuestion.setInt(1, game.getId());

                psCreateHistory.executeUpdate();
                psDeleteAnsweredQuestion.executeUpdate();
                psDeleteAppeal.executeUpdate();
                psDeleteUser_Game.executeUpdate();
                psDeleteGame.executeUpdate();


                connection.commit();
            }
        } catch (SQLException exc) {
            LOGGER.error("SQLException: " + exc.toString());
            exc.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("History was saved");
    }

    @Override
    public Optional<History> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<History> findAll() {
        List<History> histories = new ArrayList<>();
        final String query = " select * from history order by date desc";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            HistoryMapper historyMapper = new HistoryMapper();

            while (rs.next()) {
                History history = historyMapper.extractFromResultSet(rs);
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO
        }
        return histories;
    }


    @Override
    public void update(History entity) {

    }

    @Override
    public void delete(int id) {

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