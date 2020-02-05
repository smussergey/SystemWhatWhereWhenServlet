package ua.training.system_what_where_when_servlet.dao;

import ua.training.system_what_where_when_servlet.entity.Appeal;

public interface AppealDao extends GenericDao<Appeal> {
    void deleteByGameId(int gameId);
}
