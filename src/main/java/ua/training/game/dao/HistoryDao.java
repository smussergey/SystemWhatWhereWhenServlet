package ua.training.game.dao;

import ua.training.game.domain.Game;
import ua.training.game.domain.History;

public interface HistoryDao extends GenericDao<History> {
    void createAndDeleteGameRecord(History history, Game game);
}
