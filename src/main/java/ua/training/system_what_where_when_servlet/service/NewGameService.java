package ua.training.system_what_where_when_servlet.service;//package ua.training.system_what_where_when_servlet.service;

import ua.training.system_what_where_when_servlet.entity.Game;
import ua.training.system_what_where_when_servlet.entity.Question;
import ua.training.system_what_where_when_servlet.entity.User;
import ua.training.system_what_where_when_servlet.entity.exception.TwoPlayersTheSameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NewGameService {
    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();

//    public NewGameService(UserService userService, GameService gameService) {
//        this.userService = userService;
//        this.gameService = gameService;
//    }

    public void runNewGame(Integer firstPlayerId, Integer secondPlayerId, int maxNumberOfScoresToFinishGame) {

        if (firstPlayerId.equals(secondPlayerId)) {
//            log.error("IN NewGameService, method runNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            throw new TwoPlayersTheSameException("firstPlayerId and secondPlayerId are the same ");
        }

        Game game = generateNewGameWithResults(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);

        gameService.save(game);
//        return save(game);
    }

    //    @Transactional
//    public void save(Game game) {
//        gameService.save(game);
//        return gameService.save(game);
//    }

    private Game generateNewGameWithResults(Integer firstPlayerId, Integer secondPlayerId, int maxNumberOfScoresToFinishGame) {

        User firstPlayer = userService.findUserById(firstPlayerId);
        User secondPlayer = userService.findUserById(secondPlayerId);

        List<Question> questions = generateQuestionsUntilMaxNumberOfScoresIsReached(firstPlayer, secondPlayer, maxNumberOfScoresToFinishGame);

        Game game = new Game();
        game.setDate(LocalDate.now());
        game.setFirstPlayer(firstPlayer);
        game.setSecondPlayer(secondPlayer);
        game.setQuestions(questions);

        return game;
    }

    private List<Question> generateQuestionsUntilMaxNumberOfScoresIsReached(User firstPlayer, User secondPlayer,
                                                                            int maxNumberOfScoresToFinishGame) {
        int firstPlayerScoresCount = 0;
        int secondPlayerScoresCount = 0;
        List<Question> questions = new ArrayList<>();

        while (true) {
            Question question = generateQuestion(firstPlayer, secondPlayer);
            questions.add(question);

            if (firstPlayer.equals(question.getUserWhoGotPoint())) {
                firstPlayerScoresCount++;
            } else {
                secondPlayerScoresCount++;
            }

            if (firstPlayerScoresCount == maxNumberOfScoresToFinishGame
                    || secondPlayerScoresCount == maxNumberOfScoresToFinishGame) {
                break;
            }
        }
        return questions;
    }

    private Question generateQuestion(User firstPlayer, User secondPlayer) {
        Question question = new Question();
        if (ThreadLocalRandom.current().nextBoolean()) {
            question.setUserWhoGotPoint(firstPlayer);
        } else {
            question.setUserWhoGotPoint(secondPlayer);
        }
        return question;
    }
}


//    private static final Logger LOGGER = Logger.getLogger(NewGameService.class);
//    private final DaoFactory daoFactory;
//    private final ServiceFactory serviceFactory;
//
//    public NewGameService() {
//        this.daoFactory = DaoFactory.getInstance();
//        this.serviceFactory = ServiceFactory.getInstance();
//    }
//
//
//
//
//    public void runNewGame(Integer playerId, Integer opponentId, int maxNumberOfScoresToFinishGame) {
//        Game game = generateNewGameWithResults(playerId, opponentId, maxNumberOfScoresToFinishGame);
//        daoFactory.createGameDao().create(game); //TODO catch exceptions
//    }
//
//    private Game generateNewGameWithResults(int playerId, int opponentId, int maxNumberOfScoresToFinishGame) {
//        int playerScoresCount = 0;
//        int opponentScoresCount = 0;
//        List<Question> questionList = new ArrayList<>();
//
//        User player = serviceFactory.getUserService().findById(playerId).orElseThrow(() -> new EntityNotFoundException("User was not found with id =" + playerId));
//        Optional<User> opponent = serviceFactory.getUserService().findById(opponentId);
//        LOGGER.info(String.format("In NewGameService class, generateNewGameWithResults method, player = %s, opponent =%s", player, opponent.isPresent()));
//
//        while (true) {
//            Question question = generateAnsweredQuestion(player, opponent);
//            questionList.add(question);
//
//            if (player.equals(question.getUserWhoGotPoint())) {
//                playerScoresCount++;
//            } else {
//                opponentScoresCount++;
//            }
//
//            if (playerScoresCount == maxNumberOfScoresToFinishGame
//                    || opponentScoresCount == maxNumberOfScoresToFinishGame) {
//                break;
//            }
//        }
//
//        return buildNewGameToSave(player, opponent, questionList);
//    }
//
//    private Question generateAnsweredQuestion(User playingTeam, Optional<User> opponent) {
//        Question question = new Question();
//        if (ThreadLocalRandom.current().nextBoolean()) {
//            question.setUserWhoGotPoint(playingTeam);
//        } else {
//            opponent.ifPresent(opponentPlayer -> question.setUserWhoGotPoint(opponentPlayer));
//        }
//        return question;
//    }
//
//    private Game buildNewGameToSave(User player, Optional<User> opponent, List<Question> questions) {
//        Game game = new Game();
//        game.setDate(LocalDate.now());
//        game.getUsers().add(player);
//        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, player = %s, opponent =%s", player, opponent.isPresent()));
//        opponent.ifPresent(opponentPlayer -> game.getUsers().add(opponentPlayer));
//        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, in game %d players", game.getUsers().size()));
//        game.getQuestions().addAll(questions);
//        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, in game %d answeredquestions", game.getQuestions().size()));
//        return game;
//    }

//}
