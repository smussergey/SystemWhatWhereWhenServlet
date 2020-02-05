package ua.training.system_what_where_when_servlet.service;

import org.apache.log4j.Logger;
import ua.training.system_what_where_when_servlet.dto.GameDTO;
import ua.training.system_what_where_when_servlet.entity.AppealStage;
import ua.training.system_what_where_when_servlet.entity.Game;
import ua.training.system_what_where_when_servlet.entity.User;
import ua.training.system_what_where_when_servlet.util.ResourceBundleUtil;

public class GameDTOService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private static final String DELIMITER = ":";

    //TODO refactor this method
    public GameDTO toGameDTO(Game game) {
        LOGGER.info(String.format("GameDTOService class, toGameDTO is executing on a game with id = %d", game.getId()));
        GameDTO gameDTO = new GameDTO();

        gameDTO.setId(game.getId());
        gameDTO.setDate(game.getDate());


        gameDTO.setPlayerNameUa(game.getUsers().get(0).getNameUa());//TODO improve
        gameDTO.setPlayerNameEn(game.getUsers().get(0).getNameEn());//TODO improve

        if (game.getUsers().size() > 1) { //TODO improve
            gameDTO.setOpponentNameUa(game.getUsers().get(1).getNameUa());
            gameDTO.setOpponentNameEn(game.getUsers().get(1).getNameEn());
        } else {
            gameDTO.setOpponentNameUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
            gameDTO.setOpponentNameEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
        }

        User firstPlayer = game.getUsers().get(0); //TODO correct
        long firstPlayerScores = game.getAnsweredQuestions()
                .stream()
                .filter(aq -> firstPlayer.equals(aq.getUserWhoGotPoint()))
                .count();

        long secondPlayerScores = game.getAnsweredQuestions()
                .stream()
                .count() - firstPlayerScores;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstPlayerScores);
        stringBuilder.append(DELIMITER);//TODO move ":" to properties
        stringBuilder.append(secondPlayerScores);
        String scores = stringBuilder.toString();
        gameDTO.setScores(scores);

        if (game.getAppeals().isEmpty()) {
            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
        } else {
            game.getAppeals().stream()
                    .forEach(appeal -> {
                        if (appeal.getAppealStage().equals(AppealStage.FILED)) {
                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
                        } else {
                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.CONSIDERED.name()));
                        }

                    });
        }
        return gameDTO;
    }

}
