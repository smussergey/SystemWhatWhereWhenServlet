package ua.training.game.dao;

        import ua.training.game.domain.Appeal;

public interface AppealDao extends GenericDao<Appeal> {
    void deleteByGameId(int gameId);
}
