package ua.training.system_what_where_when_servlet.dao.mapper;

import ua.training.system_what_where_when_servlet.entity.Appeal;
import ua.training.system_what_where_when_servlet.entity.AppealStage;
import ua.training.system_what_where_when_servlet.entity.Game;

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
