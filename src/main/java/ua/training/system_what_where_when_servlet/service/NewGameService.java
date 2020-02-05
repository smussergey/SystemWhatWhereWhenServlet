package ua.training.system_what_where_when_servlet.service;//package ua.training.system_what_where_when_servlet.service;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.entity.AnsweredQuestion;
import ua.training.system_what_where_when_servlet.entity.Game;
import ua.training.system_what_where_when_servlet.entity.User;
import ua.training.system_what_where_when_servlet.entity.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


public class NewGameService {
    private static final Logger LOGGER = Logger.getLogger(NewGameService.class);
    private final DaoFactory daoFactory;
    private final ServiceFactory serviceFactory;

    public NewGameService() {
        this.daoFactory = DaoFactory.getInstance();
        this.serviceFactory = ServiceFactory.getInstance();
    }




    public void runNewGame(Integer playerId, Integer opponentId, int maxNumberOfScoresToFinishGame) {
        Game game = generateNewGameWithResults(playerId, opponentId, maxNumberOfScoresToFinishGame);
        daoFactory.createGameDao().create(game); //TODO catch exceptions
    }

    private Game generateNewGameWithResults(int playerId, int opponentId, int maxNumberOfScoresToFinishGame) {
        int playerScoresCount = 0;
        int opponentScoresCount = 0;
        List<AnsweredQuestion> answeredQuestionList = new ArrayList<>();

        User player = serviceFactory.getUserService().findById(playerId).orElseThrow(() -> new EntityNotFoundException("User was not found with id =" + playerId));
        Optional<User> opponent = serviceFactory.getUserService().findById(opponentId);
        LOGGER.info(String.format("In NewGameService class, generateNewGameWithResults method, player = %s, opponent =%s", player, opponent.isPresent()));

        while (true) {
            AnsweredQuestion answeredQuestion = generateAnsweredQuestion(player, opponent);
            answeredQuestionList.add(answeredQuestion);

            if (player.equals(answeredQuestion.getUserWhoGotPoint())) {
                playerScoresCount++;
            } else {
                opponentScoresCount++;
            }

            if (playerScoresCount == maxNumberOfScoresToFinishGame
                    || opponentScoresCount == maxNumberOfScoresToFinishGame) {
                break;
            }
        }

        return buildNewGameToSave(player, opponent, answeredQuestionList);
    }

    private AnsweredQuestion generateAnsweredQuestion(User playingTeam,  Optional<User> opponent) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion();
        if (ThreadLocalRandom.current().nextBoolean()) {
            answeredQuestion.setUserWhoGotPoint(playingTeam);
        } else {
            opponent.ifPresent(opponentPlayer -> answeredQuestion.setUserWhoGotPoint(opponentPlayer));
        }
        return answeredQuestion;
    }

    private Game buildNewGameToSave(User player, Optional<User> opponent, List<AnsweredQuestion> answeredQuestions) {
        Game game = new Game();
        game.setDate(LocalDate.now());
        game.getUsers().add(player);
        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, player = %s, opponent =%s", player, opponent.isPresent()));
        opponent.ifPresent(opponentPlayer -> game.getUsers().add(opponentPlayer));
        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, in game %d players", game.getUsers().size()));
        game.getAnsweredQuestions().addAll(answeredQuestions);
        LOGGER.info(String.format("In NewGameService class, buildNewGameToSave method, in game %d answeredquestions", game.getAnsweredQuestions().size()));
        return game;
    }

}
