package ua.training.game.dao.mapper;

import ua.training.game.domain.History;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class HistoryMapper implements ObjectMapper<History> {
    @Override
    public History extractFromResultSet(ResultSet rs) throws SQLException {
        History history = new History();
        history.setId(rs.getInt("history.history_id"));
        history.setDate(rs.getDate("history.date").toLocalDate());
        history.setFirstPlayerNameEn(rs.getString("history.first_player_name_en"));
        history.setFirstPlayerNameUa(rs.getString("history.first_player_name_ua"));
        history.setSecondPlayerNameEn(rs.getString("history.second_player_name_en"));
        history.setSecondPlayerNameUa(rs.getString("history.second_player_name_ua"));
        history.setScores(rs.getString("history.scores"));
        history.setAppealStage(rs.getString("history.appeal_stage"));

        return history;
    }

    @Override
    public History makeUnique(Map<Integer, History> cache, History entity) {
        throw new UnsupportedOperationException("This method is not implemented");
    }
}
