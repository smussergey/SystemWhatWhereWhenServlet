package ua.training.game.dao;

import ua.training.game.domain.Appeal;

import java.util.List;

public interface AppealDao extends GenericDao<Appeal> {
    void deleteByGameId(int gameId);
    void update (List<Appeal> appeals);

}
