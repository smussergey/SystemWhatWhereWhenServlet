package ua.training.system_what_where_when_servlet.dao.mapper;

import ua.training.system_what_where_when_servlet.dto.UserDTO;
import ua.training.system_what_where_when_servlet.entity.History;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class HistoryMapper implements ObjectMapper<History> {
    @Override
    public History extractFromResultSet(ResultSet rs) throws SQLException {
        History history = new History();
        history.setId(rs.getInt("history_id"));
        history.setDate(rs.getDate("date").toLocalDate());
        history.setPlayerNameEn(rs.getString("player_name_en"));
        history.setPlayerNameUa(rs.getString("player_name_ua"));
        history.setOpponentNameEn(rs.getString("opponent_name_en"));
        history.setOpponentNameUa(rs.getString("opponent_name_ua"));
        history.setScores(rs.getString("scores"));
        history.setAppealStage(rs.getString("appeal_stage"));

        return history;
    }

    @Override
    public History makeUnique(Map<Integer, History> cache, History entity) {
        throw new UnsupportedOperationException("This method is not implemented");
    }
}
