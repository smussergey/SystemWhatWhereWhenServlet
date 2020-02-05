package ua.training.system_what_where_when_servlet.dao.impl;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.GameDao;
import ua.training.system_what_where_when_servlet.dao.mapper.AnsweredQuestionMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.AppealMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.GameMapper;
import ua.training.system_what_where_when_servlet.dao.mapper.UserMapper;
import ua.training.system_what_where_when_servlet.entity.*;
import ua.training.system_what_where_when_servlet.entity.exception.NotUniqueLoginException;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class GameDaoImpl implements GameDao {
    private static final Logger LOGGER = Logger.getLogger(GameDaoImpl.class);
    private Connection connection;

    public GameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Game game) {
        LOGGER.info(String.format("In GameDaoImpl, method create game: " + game));
        ResultSet rs;
        int gameId;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psGame = connection.prepareStatement
                    ("INSERT INTO game (date)" +
                            " VALUES (?)", Statement.RETURN_GENERATED_KEYS);

                 PreparedStatement psAnsweredQuestionInGameWithTwoPlayers = connection.prepareStatement
                         ("INSERT INTO answered_question (game_id, user_id)" +
                                 " VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

                 PreparedStatement psAnsweredQuestionInGameWithOnePlayer = connection.prepareStatement
                         ("INSERT INTO answered_question (game_id)" +
                                 " VALUES (?)", Statement.RETURN_GENERATED_KEYS);

                 PreparedStatement psUserGame = connection.prepareStatement
                         ("INSERT INTO user_game (game_id, user_id)" +
                                 " VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {


                psGame.setDate(1, Date.valueOf(game.getDate()));

                int rowAffected = psGame.executeUpdate();
                LOGGER.info(String.format("In GameDaoImpl, method create game, after game insert: " + game));


                if (rowAffected == 0) {
                    throw new SQLException("Creating Game failed, no rows affected.");
                }
                rs = psGame.getGeneratedKeys();

                if (rs.next()) {
                    gameId = rs.getInt(1);

                    List<User> users = game.getUsers();
                    for (int i = 0; i < users.size(); i++) { //TODO improve
                        LOGGER.info(String.format("In GameDaoImpl, method create game, for i (users) = %d", i));
                        psUserGame.setInt(1, gameId);
                        psUserGame.setInt(2, users.get(i).getId());
                        LOGGER.info(String.format("In GameDaoImpl, method create game, before insert into user_game: %d, %d : ", gameId, users.get(i).getId()));
                        psUserGame.executeUpdate();
                        LOGGER.info(String.format("In GameDaoImpl, method create game, after insert into user_game: %d, %d : ", gameId, users.get(i).getId()));
                    }

                    List<AnsweredQuestion> answeredQuestions = game.getAnsweredQuestions();

                    for (int i = 0; i < answeredQuestions.size(); i++) { //TODO improve
                        LOGGER.info(String.format("In GameDaoImpl, method create game, aq size = %d", answeredQuestions.size()));
                        LOGGER.info(String.format("In GameDaoImpl, method create game, for i(aq) = %d", i));

                        if (answeredQuestions.get(i).getUserWhoGotPoint() != null) { //TODO improve
                            LOGGER.info(String.format("In GameDaoImpl, method create game, before insert into answeredQuestion: gameId = %d, adId = %d : ", gameId, answeredQuestions.get(i).getId()));
                            psAnsweredQuestionInGameWithTwoPlayers.setInt(1, gameId);
                            psAnsweredQuestionInGameWithTwoPlayers.setInt(2, answeredQuestions.get(i).getUserWhoGotPoint().getId());
                            psAnsweredQuestionInGameWithTwoPlayers.executeUpdate();
                        } else {
                            psAnsweredQuestionInGameWithOnePlayer.setInt(1, gameId);
                            psAnsweredQuestionInGameWithOnePlayer.executeUpdate();
                            LOGGER.info(String.format("In GameDaoImpl, method create game, before insert into answeredQuestion: %d, user=null : ", gameId));
                        }

                    }
                }
                connection.commit();
                LOGGER.info("Game was saved");
            }
            connection.rollback();
        } catch (
                SQLException ex) {

//TODO implement
            ex.printStackTrace();


        }

    }


    @Override
    public Optional<Game> findById(int id) {
        Map<Integer, AnsweredQuestion> answeredQuestions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();


        Optional<Game> result = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement("" +
                " select * from user " +
                " left join user_game " +
                " on  user.user_id = user_game.user_id " +
                " left join game " +
                " on user_game.game_id = game.game_id " +
                " left join answered_question " +
                " on game.game_id = answered_question.game_id " +
                " left join appeal " +
                " on answered_question.appeal_id = appeal.appeal_id " +
                " where game.game_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs;

            rs = ps.executeQuery();

            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);


                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
                        appeal.setUser(user);
                    }
                    game.getAppeals().add(appeal);
                }

                AnsweredQuestion answeredQuestion = answeredQuestionMapper
                        .extractFromResultSet(rs);
                answeredQuestion = answeredQuestionMapper
                        .makeUnique(answeredQuestions, answeredQuestion);
                answeredQuestion.setGame(game);
                if (rs.getInt("answered_question.user_id") > 0)
                    answeredQuestion.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
                if (rs.getInt("answered_question.appeal_id") > 0)
                    answeredQuestion.setAppeal(appeal);

                if (game.getAnsweredQuestions().contains(answeredQuestion)) {
                } else {
                    game.getAnsweredQuestions().add(answeredQuestion);
                }
//                game.getAnsweredQuestions().add(answeredQuestion);

                if (game.getUsers().contains(user)) {
                } else {
                    game.getUsers().add(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception in class: UserDaoImpl, method: findById.", ex);
            throw new RuntimeException(ex); //TODO Correct
        }
        return result = games.values().stream().findFirst();
    }

    @Override
    public List<Game> findAll() {
        Map<Integer, AnsweredQuestion> answeredQuestions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        final String query = "" +
                " select * from user " +
                " left join user_game " +
                " on  user.user_id = user_game.user_id " +
                " left join game " +
                " on user_game.game_id = game.game_id " +
                " left join answered_question " +
                " on game.game_id = answered_question.game_id " +
                " left join appeal " +
                " on answered_question.appeal_id = appeal.appeal_id " +
                " where game.game_id>0"; // TODO correct

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);


                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
                        appeal.setUser(user);
                    }
                    game.getAppeals().add(appeal);
                }

                AnsweredQuestion answeredQuestion = answeredQuestionMapper
                        .extractFromResultSet(rs);
                answeredQuestion = answeredQuestionMapper
                        .makeUnique(answeredQuestions, answeredQuestion);
                answeredQuestion.setGame(game);
                if (rs.getInt("answered_question.user_id") > 0)
                    answeredQuestion.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
                if (rs.getInt("answered_question.appeal_id") > 0)
                    answeredQuestion.setAppeal(appeal);

                if (game.getAnsweredQuestions().contains(answeredQuestion)) {
                } else {
                    game.getAnsweredQuestions().add(answeredQuestion);
                }
//                game.getAnsweredQuestions().add(answeredQuestion);

                if (game.getUsers().contains(user)) {
                } else {
                    game.getUsers().add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(games.values());
    }

    @Override
    public List<Game> findAllByUsername(String username) {
        Map<Integer, AnsweredQuestion> answeredQuestions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();


        Optional<Game> result = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement("" +
                " select * from user " +
                " left join user_game " +
                " on  user.user_id = user_game.user_id " +
                " left join game " +
                " on user_game.game_id = game.game_id " +
                " left join answered_question " +
                " on game.game_id = answered_question.game_id " +
                " left join appeal " +
                " on answered_question.appeal_id = appeal.appeal_id " +
                " where user.email = ?")) {

            ps.setString(1, username);
            ResultSet rs;

            rs = ps.executeQuery();

            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);


                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
                        appeal.setUser(user);
                    }
                    game.getAppeals().add(appeal);
                }

                AnsweredQuestion answeredQuestion = answeredQuestionMapper
                        .extractFromResultSet(rs);
                answeredQuestion = answeredQuestionMapper
                        .makeUnique(answeredQuestions, answeredQuestion);
                answeredQuestion.setGame(game);
                if (rs.getInt("answered_question.user_id") > 0)
                    answeredQuestion.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
                if (rs.getInt("answered_question.appeal_id") > 0)
                    answeredQuestion.setAppeal(appeal);

                if (game.getAnsweredQuestions().contains(answeredQuestion)) {
                } else {
                    game.getAnsweredQuestions().add(answeredQuestion);
                }
//                game.getAnsweredQuestions().add(answeredQuestion);

                if (game.getUsers().contains(user)) {
                } else {
                    game.getUsers().add(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception in class: UserDaoImpl, method: findById.", ex);
            throw new RuntimeException(ex); //TODO Correct
        }
        return new ArrayList<>(games.values());
    }

//TODO implement query for date too
    @Override
    public List<Game> findAllByAppealStageAndDateLaterThan(AppealStage appealStage, LocalDate localDate) {
        Map<Integer, AnsweredQuestion> answeredQuestions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();


        Optional<Game> result = Optional.empty();
        try (PreparedStatement ps = connection.prepareStatement("" +
                " select * from user " +
                " left join user_game " +
                " on  user.user_id = user_game.user_id " +
                " left join game " +
                " on user_game.game_id = game.game_id " +
                " left join answered_question " +
                " on game.game_id = answered_question.game_id " +
                " left join appeal " +
                " on answered_question.appeal_id = appeal.appeal_id " +
                " where appeal.appeal_stage = ?")) {

            ps.setString(1, appealStage.name());
            ResultSet rs;

            rs = ps.executeQuery();

            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);


                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
                        appeal.setUser(user);
                    }
                    game.getAppeals().add(appeal);
                }

                AnsweredQuestion answeredQuestion = answeredQuestionMapper
                        .extractFromResultSet(rs);
                answeredQuestion = answeredQuestionMapper
                        .makeUnique(answeredQuestions, answeredQuestion);
                answeredQuestion.setGame(game);
                if (rs.getInt("answered_question.user_id") > 0)
                    answeredQuestion.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
                if (rs.getInt("answered_question.appeal_id") > 0)
                    answeredQuestion.setAppeal(appeal);

                if (game.getAnsweredQuestions().contains(answeredQuestion)) {
                } else {
                    game.getAnsweredQuestions().add(answeredQuestion);
                }
//                game.getAnsweredQuestions().add(answeredQuestion);

                if (game.getUsers().contains(user)) {
                } else {
                    game.getUsers().add(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("Exception in class: GameDaoImpl, method: findAllByAppealStageAndDateLaterThan.", ex);
            throw new RuntimeException(ex); //TODO Correct
        }
        return new ArrayList<>(games.values());
    }

    @Override
    public void update(Game entity) {

    }

    @Override
    public void delete(int id) {
        LOGGER.info(String.format("method delete by id = %d", id));

        try (PreparedStatement ps = connection.prepareStatement
                ("DELETE  FROM game where game_id = ?")) {

            ps.setInt(1, id);
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
