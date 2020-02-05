package ua.training.system_what_where_when_servlet.dao.mapper;

import ua.training.system_what_where_when_servlet.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDTOMapper implements ObjectMapper<UserDTO> {
    @Override
    public UserDTO extractFromResultSet(ResultSet rs) throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(rs.getInt("user.user_id"));
        userDTO.setNameUa(rs.getString("user.name_ua"));
        userDTO.setNameEn(rs.getString("user.name_en"));
        return userDTO;
    }

    @Override
    public UserDTO makeUnique(Map<Integer, UserDTO> cache, UserDTO teacher) {
        throw new UnsupportedOperationException("This method is not implemented");
    }
}
