package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.GameDao;
import ua.training.game.dao.connection.ConnectionPoolHolder;
import ua.training.game.dao.mapper.*;
import ua.training.game.domain.*;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class JDBCGameDao implements GameDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCGameDao.class);
    private static final String INSERT_GAME_QUERY = "INSERT INTO game (date, first_player_user_id, second_player_user_id) VALUES (?, ?, ?)";
    private static final String INSERT_QUESTIONS_QUERY = "INSERT INTO question (game_id, user_id) VALUES (?,?)";

    public JDBCGameDao() {
    }

    @Override
    public void create(Game game) {
        LOGGER.info(String.format("In GameDaoImpl, method create game: " + game));
        ResultSet rs;
        int gameId;

        try (Connection connection = ConnectionPoolHolder.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement psInsertGame = connection.prepareStatement
                    (INSERT_GAME_QUERY, Statement.RETURN_GENERATED_KEYS);

                 PreparedStatement psInsertQuestions = connection.prepareStatement
                         (INSERT_QUESTIONS_QUERY)) {
                psInsertGame.setDate(1, Date.valueOf(game.getDate()));
                psInsertGame.setInt(2, game.getFirstPlayer().getId());
                psInsertGame.setInt(3, game.getSecondPlayer().getId());

                int rowAffected = psInsertGame.executeUpdate();
                LOGGER.info(String.format("In GameDaoImpl, method create game, after game insert: " + game));

                if (rowAffected == 0) {
                    throw new SQLException("Creating Game failed, no rows affected.");
                }
                rs = psInsertGame.getGeneratedKeys();

                if (rs.next()) {
                    gameId = rs.getInt(1);

                    List<Question> questions = game.getQuestions();

                    for (int i = 0; i < questions.size(); i++) { //TODO improve
                        LOGGER.info(String.format("In GameDaoImpl, method create game, aq size = %d", questions.size()));
                        LOGGER.info(String.format("In GameDaoImpl, method create game, for i(aq) = %d", i));

                        LOGGER.info(String.format("In GameDaoImpl, method create game, before insert into answeredQuestion: gameId = %d, adId = %d : ", gameId, questions.get(i).getId()));
                        psInsertQuestions.setInt(1, gameId);
                        psInsertQuestions.setInt(2, questions.get(i).getUserWhoGotPoint().getId());
                        psInsertQuestions.executeUpdate();
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
    public Optional<Game> findById(int id) {
        Map<Integer, Question> questions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();
        Map<Integer, AppealedQuestion> appealedQuestions = new HashMap<>();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement("" +
                     "SELECT * FROM game " +
                     " left join user as us1 " +
                     " on game.first_player_user_id = us1.user_id " +
                     " left join user as us2 " +
                     " on game.second_player_user_id = us2.user_id " +
                     " left join question " +
                     " on game.game_id = question.game_id " +
                     " left join appeal " +
                     " on game.game_id = appeal.game_id " +
                     " left join appealed_question " +
                     " on appeal.appeal_id = appealed_question.appeal_id " +
                     " where game.game_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            QuestionMapper questionMapper = new QuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();
            AppealedQuestionMapper appealedQuestionMapper = new AppealedQuestionMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User firstPlayer = userMapper
                        .extractFromResultSetForFirstPlayer(rs);
                firstPlayer = userMapper
                        .makeUnique(users, firstPlayer);

                game.setFirstPlayer(firstPlayer);

                User secondPlayer = userMapper
                        .extractFromResultSetForSecondPlayer(rs);
                secondPlayer = userMapper
                        .makeUnique(users, secondPlayer);

                game.setSecondPlayer(secondPlayer);

                Question question = questionMapper
                        .extractFromResultSet(rs);
                question = questionMapper
                        .makeUnique(questions, question);
                question.setGame(game);
                question.setUserWhoGotPoint(users.get(rs.getInt("question.user_id")));


                if (game.getQuestions().contains(question)) {
                } else {
                    game.getQuestions().add(question);
                }

                if (rs.getInt("appeal.appeal_id") > 0) {
                    Appeal appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    appeal.setUser(users.get(rs.getInt("appeal.user_id")));
                    appeal.setGame(games.get(rs.getInt("appeal.game_id"))); // TODO check whether it is needed

                    AppealedQuestion appealedQuestion = appealedQuestionMapper.extractFromResultSet(rs);
                    appealedQuestion = appealedQuestionMapper
                            .makeUnique(appealedQuestions, appealedQuestion);
                    appealedQuestion.setAppeal(appeals.get(rs.getInt("appealed_question.appeal_id")));
                    appealedQuestion.setQuestion(questions.get(rs.getInt("appealed_question.question_id")));
                    appeal.getAppealedQuestions().add(appealedQuestion);

                    if (game.getAppeals().contains(appeal)) {
                    } else {
                        game.getAppeals().add(appeal);
                    }
//                    game.getAppeals().add(appeal);
                }
            }
            LOGGER.info("In findById method:");
            return games.values().stream().findFirst();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new EntityNotFoundException("not found"); // TODO correct
        }
    }

    @Override
    public List<Game> findAll() {
        Map<Integer, Question> questions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        final String query = "" +
                "SELECT * FROM game " +
                " left join user as us1 " +
                " on game.first_player_user_id = us1.user_id " +
                " left join user as us2 " +
                " on game.second_player_user_id = us2.user_id " +
                " left join question " +
                " on game.game_id = question.game_id " +
                " left join appeal " +
                " on game.game_id = appeal.game_id " +
                " where game.game_id>0"; // TODO correct

        try (Connection connection = ConnectionPoolHolder.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            QuestionMapper questionMapper = new QuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User firstPlayer = userMapper
                        .extractFromResultSetForFirstPlayer(rs);
                firstPlayer = userMapper
                        .makeUnique(users, firstPlayer);

                game.setFirstPlayer(firstPlayer);

                User secondPlayer = userMapper
                        .extractFromResultSetForSecondPlayer(rs);
                secondPlayer = userMapper
                        .makeUnique(users, secondPlayer);

                game.setSecondPlayer(secondPlayer);

                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    appeal.setUser(users.get(rs.getInt("appeal.user_id")));
                    appeal.setGame(games.get(rs.getInt("appeal.game_id"))); // TODO check whether it is needed

                    if (game.getAppeals().contains(appeal)) {
                    } else {
                        game.getAppeals().add(appeal);
                    }
//                    game.getAppeals().add(appeal);
                }

                Question question = questionMapper
                        .extractFromResultSet(rs);
                question = questionMapper
                        .makeUnique(questions, question);
                question.setGame(game);
                question.setUserWhoGotPoint(users.get(rs.getInt("question.user_id")));


                if (game.getQuestions().contains(question)) {
                } else {
                    game.getQuestions().add(question);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
        return new ArrayList<>(games.values());
    }

    @Override //TODO rename method find by player
    public List<Game> findAllByFirstPlayerOrSecondPlayer(User firstUser, User secondUser) {
        Map<Integer, Question> questions = new HashMap<>();
        Map<Integer, Appeal> appeals = new HashMap<>();
        Map<Integer, Game> games = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement("" +
                     "SELECT * FROM game " +
                     " left join user as us1 " +
                     " on game.first_player_user_id = us1.user_id " +
                     " left join user as us2 " +
                     " on game.second_player_user_id = us2.user_id " +
                     " left join question " +
                     " on game.game_id = question.game_id " +
                     " left join appeal " +
                     " on game.game_id = appeal.game_id " +
                     " where game.first_player_user_id = ?" +
                     " or game.second_player_user_id = ?")) {

            ps.setInt(1, firstUser.getId());
            ps.setInt(2, secondUser.getId());
            ResultSet rs = ps.executeQuery();

            QuestionMapper questionMapper = new QuestionMapper();
            AppealMapper appealMapper = new AppealMapper();
            GameMapper gameMapper = new GameMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                Game game = gameMapper
                        .extractFromResultSet(rs);
                game = gameMapper
                        .makeUnique(games, game);

                User firstPlayer = userMapper
                        .extractFromResultSetForFirstPlayer(rs);
                firstPlayer = userMapper
                        .makeUnique(users, firstPlayer);

                game.setFirstPlayer(firstPlayer);

                User secondPlayer = userMapper
                        .extractFromResultSetForSecondPlayer(rs);
                secondPlayer = userMapper
                        .makeUnique(users, secondPlayer);

                game.setSecondPlayer(secondPlayer);

                Appeal appeal = null;

                if (rs.getInt("appeal.appeal_id") > 0) {
                    appeal = appealMapper
                            .extractFromResultSet(rs);
                    appeal = appealMapper
                            .makeUnique(appeals, appeal);
                    appeal.setUser(users.get(rs.getInt("appeal.user_id")));
                    appeal.setGame(games.get(rs.getInt("appeal.game_id"))); // TODO check whether it is needed


                    if (game.getAppeals().contains(appeal)) {
                    } else {
                        game.getAppeals().add(appeal);
                    }


//                    game.getAppeals().add(appeal);
                }

                    Question question = questionMapper
                            .extractFromResultSet(rs);
                    question = questionMapper
                            .makeUnique(questions, question);
                    question.setGame(game);
                    question.setUserWhoGotPoint(users.get(rs.getInt("question.user_id")));


                    if (game.getQuestions().contains(question)) {
                    } else {
                        game.getQuestions().add(question);
                    }
                }
            } catch(SQLException ex){
                LOGGER.error(ex.getMessage());
                throw new RuntimeException(ex); //TODO check
            }
            return new ArrayList<>(games.values());
        }


        @Override
        public List<Game> findAllByUsername (String username){
//        Map<Integer, Question> answeredQuestions = new HashMap<>();
//        Map<Integer, Appeal> appeals = new HashMap<>();
//        Map<Integer, Game> games = new HashMap<>();
//        Map<Integer, User> users = new HashMap<>();
//
//
//        Optional<Game> result = Optional.empty();
//        try (PreparedStatement ps = connection.prepareStatement("" +
//                " select * from user " +
//                " left join user_game " +
//                " on  user.user_id = user_game.user_id " +
//                " left join game " +
//                " on user_game.game_id = game.game_id " +
//                " left join answered_question " +
//                " on game.game_id = answered_question.game_id " +
//                " left join appeal " +
//                " on answered_question.appeal_id = appeal.appeal_id " +
//                " where user.email = ?")) {
//
//            ps.setString(1, username);
//            ResultSet rs;
//
//            rs = ps.executeQuery();
//
//            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
//            AppealMapper appealMapper = new AppealMapper();
//            GameMapper gameMapper = new GameMapper();
//            UserMapper userMapper = new UserMapper();
//
//            while (rs.next()) {
//                Game game = gameMapper
//                        .extractFromResultSet(rs);
//                game = gameMapper
//                        .makeUnique(games, game);
//
//                User user = userMapper
//                        .extractFromResultSet(rs);
//                user = userMapper
//                        .makeUnique(users, user);
//
//
//                Appeal appeal = null;
//
//                if (rs.getInt("appeal.appeal_id") > 0) {
//                    appeal = appealMapper
//                            .extractFromResultSet(rs);
//                    appeal = appealMapper
//                            .makeUnique(appeals, appeal);
//                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
//                        appeal.setUser(user);
//                    }
//                    game.getAppeals().add(appeal);
//                }
//
//                Question question = answeredQuestionMapper
//                        .extractFromResultSet(rs);
//                question = answeredQuestionMapper
//                        .makeUnique(answeredQuestions, question);
//                question.setGame(game);
//                if (rs.getInt("answered_question.user_id") > 0)
//                    question.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
//                if (rs.getInt("answered_question.appeal_id") > 0)
//                    question.setAppeal(appeal);
//
//                if (game.getQuestions().contains(question)) {
//                } else {
//                    game.getQuestions().add(question);
//                }
////                game.getAnsweredQuestions().add(answeredQuestion);
//
//                if (game.getUsers().contains(user)) {
//                } else {
//                    game.getUsers().add(user);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.error("Exception in class: UserDaoImpl, method: findById.", ex);
//            throw new RuntimeException(ex); //TODO Correct
//        }
//        return new ArrayList<>(games.values());
            return null; //TODO Correct
        }

        //TODO implement query for date too
        @Override
        public List<Game> findAllByAppealStageAndDateLaterThan (AppealStage appealStage, LocalDate localDate){
//        Map<Integer, Question> answeredQuestions = new HashMap<>();
//        Map<Integer, Appeal> appeals = new HashMap<>();
//        Map<Integer, Game> games = new HashMap<>();
//        Map<Integer, User> users = new HashMap<>();
//
//
//        Optional<Game> result = Optional.empty();
//        try (PreparedStatement ps = connection.prepareStatement("" +
//                " select * from user " +
//                " left join user_game " +
//                " on  user.user_id = user_game.user_id " +
//                " left join game " +
//                " on user_game.game_id = game.game_id " +
//                " left join answered_question " +
//                " on game.game_id = answered_question.game_id " +
//                " left join appeal " +
//                " on answered_question.appeal_id = appeal.appeal_id " +
//                " where appeal.appeal_stage = ?")) {
//
//            ps.setString(1, appealStage.name());
//            ResultSet rs;
//
//            rs = ps.executeQuery();
//
//            AnsweredQuestionMapper answeredQuestionMapper = new AnsweredQuestionMapper();
//            AppealMapper appealMapper = new AppealMapper();
//            GameMapper gameMapper = new GameMapper();
//            UserMapper userMapper = new UserMapper();
//
//            while (rs.next()) {
//                Game game = gameMapper
//                        .extractFromResultSet(rs);
//                game = gameMapper
//                        .makeUnique(games, game);
//
//                User user = userMapper
//                        .extractFromResultSet(rs);
//                user = userMapper
//                        .makeUnique(users, user);
//
//
//                Appeal appeal = null;
//
//                if (rs.getInt("appeal.appeal_id") > 0) {
//                    appeal = appealMapper
//                            .extractFromResultSet(rs);
//                    appeal = appealMapper
//                            .makeUnique(appeals, appeal);
//                    if (rs.getInt("appeal.user_id") == rs.getInt("user.user_id")) {
//                        appeal.setUser(user);
//                    }
//                    game.getAppeals().add(appeal);
//                }
//
//                Question question = answeredQuestionMapper
//                        .extractFromResultSet(rs);
//                question = answeredQuestionMapper
//                        .makeUnique(answeredQuestions, question);
//                question.setGame(game);
//                if (rs.getInt("answered_question.user_id") > 0)
//                    question.setUserWhoGotPoint(users.get(rs.getInt("answered_question.user_id")));
//                if (rs.getInt("answered_question.appeal_id") > 0)
//                    question.setAppeal(appeal);
//
//                if (game.getQuestions().contains(question)) {
//                } else {
//                    game.getQuestions().add(question);
//                }
////                game.getAnsweredQuestions().add(answeredQuestion);
//
//                if (game.getUsers().contains(user)) {
//                } else {
//                    game.getUsers().add(user);
//                }
//            }
//        } catch (SQLException ex) {
//            LOGGER.error("Exception in class: GameDaoImpl, method: findAllByAppealStageAndDateLaterThan.", ex);
//            throw new RuntimeException(ex); //TODO Correct
//        }
//        return new ArrayList<>(games.values());
            return null; //TODO Correct
        }

        @Override
        public void update (Game entity){

        }

        @Override
        public void delete ( int id){
//        LOGGER.info(String.format("method delete by id = %d", id));
//
//        try (PreparedStatement ps = connection.prepareStatement
//                ("DELETE  FROM game where game_id = ?")) {
//
//            ps.setInt(1, id);
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace(); //TODO redo
//        }
//    }
//
        }
    }