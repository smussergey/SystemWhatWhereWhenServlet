package ua.training.game.dao.mapper;

import ua.training.game.domain.User;
import ua.training.game.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user.user_id"));
        user.setNameUa(rs.getString("user.name_ua"));
        user.setNameEn(rs.getString("user.name_en"));
        user.setEmail(rs.getString("user.email"));
        user.setPassword(rs.getString("user.password"));
        user.setRole(Role.valueOf(rs.getString("user.role")));

        return user;
    }

    public User extractFromResultSetForFirstPlayer(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("us1.user_id"));
        user.setNameUa(rs.getString("us1.name_ua"));
        user.setNameEn(rs.getString("us1.name_en"));
        user.setEmail(rs.getString("us1.email"));
        user.setPassword(rs.getString("us1.password"));
        user.setRole(Role.valueOf(rs.getString("us1.role")));

        return user;
    }

    public User extractFromResultSetForSecondPlayer(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("us2.user_id"));
        user.setNameUa(rs.getString("us2.name_ua"));
        user.setNameEn(rs.getString("us2.name_en"));
        user.setEmail(rs.getString("us2.email"));
        user.setPassword(rs.getString("us2.password"));
        user.setRole(Role.valueOf(rs.getString("us2.role")));

        return user;
    }
    public User extractFromResultSetForAppeal(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("userappealed.user_id"));
        user.setNameUa(rs.getString("userappealed.name_ua"));
        user.setNameEn(rs.getString("userappealed.name_en"));
        user.setEmail(rs.getString("userappealed.email"));
        user.setPassword(rs.getString("userappealed.password"));
        user.setRole(Role.valueOf(rs.getString("userappealed.role")));

        return user;
    }


    @Override
    public User makeUnique(Map<Integer, User> cache,
                           User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
