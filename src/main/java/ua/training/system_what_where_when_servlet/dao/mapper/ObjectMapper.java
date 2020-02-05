package ua.training.system_what_where_when_servlet.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet rs) throws SQLException;

    T makeUnique(Map<Integer, T> cache,
                 T entity);
}
