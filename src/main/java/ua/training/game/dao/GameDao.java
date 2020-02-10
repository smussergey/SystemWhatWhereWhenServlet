package ua.training.game.dao;

import ua.training.game.domain.Game;
import ua.training.game.domain.User;
import ua.training.game.enums.AppealStage;

import java.time.LocalDate;
import java.util.List;

public interface GameDao extends GenericDao<Game> {
    List<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer);

    List<Game> findAllByUsername(String username);

    List<Game> findAllByAppealStageAndDateLaterThan(AppealStage appealStage, LocalDate localDate);
}
