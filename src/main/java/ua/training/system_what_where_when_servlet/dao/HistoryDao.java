package ua.training.system_what_where_when_servlet.dao;

import ua.training.system_what_where_when_servlet.entity.Game;
import ua.training.system_what_where_when_servlet.entity.History;

public interface HistoryDao extends GenericDao<History> {
    void createAndDeleteGameRecords(History history, Game game);
}
