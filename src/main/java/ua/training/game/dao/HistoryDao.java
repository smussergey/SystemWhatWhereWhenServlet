package ua.training.game.dao;

import ua.training.game.domain.Game;
import ua.training.game.domain.History;
import ua.training.game.web.pagination.Page;

public interface HistoryDao extends GenericDao<History> {
    void createAndDeleteGameRecord(History history, Game game);

    int getNumberOfRows();

    Page<History> getHistoryGameStatisticsByAllGamesAndPlayers(int currentPage, int recordsPerPage);
}
