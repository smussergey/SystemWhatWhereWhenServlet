package ua.training.game.service;//package ua.training.system_what_where_when_servlet.service;

import ua.training.game.domain.Game;
import ua.training.game.domain.Question;
import ua.training.game.domain.User;
import ua.training.game.exception.TwoPlayersTheSameException;

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

    public int runNewGame(Integer firstPlayerId, Integer secondPlayerId, int maxNumberOfScoresToFinishGame) {

        if (firstPlayerId.equals(secondPlayerId)) {
//            log.error("IN NewGameService, method runNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            throw new TwoPlayersTheSameException("firstPlayerId and secondPlayerId are the same ");
        }

        Game game = generateNewGameWithResults(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);

        return gameService.save(game);
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
