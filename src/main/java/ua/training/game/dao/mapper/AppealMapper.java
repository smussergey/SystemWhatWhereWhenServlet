package ua.training.game.dao.mapper;

import ua.training.game.domain.Appeal;
import ua.training.game.enums.AppealStage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AppealMapper implements ObjectMapper<Appeal> {

    @Override
    public Appeal extractFromResultSet(ResultSet rs) throws SQLException {
        Appeal appeal = new Appeal();
        appeal.setId(rs.getInt("appeal.appeal_id"));
        appeal.setAppealStage(AppealStage.valueOf(rs.getString("appeal.appeal_stage")));
        appeal.setDate(rs.getDate("appeal.date").toLocalDate());
        return appeal;
    }

    @Override
    public Appeal makeUnique(Map<Integer, Appeal> cache,
                             Appeal appeal) {
        cache.putIfAbsent(appeal.getId(), appeal);
        return cache.get(appeal.getId());
    }
}
