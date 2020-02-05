package ua.training.system_what_where_when_servlet.dao;

import ua.training.system_what_where_when_servlet.dto.UserDTO;
import ua.training.system_what_where_when_servlet.entity.Role;
import ua.training.system_what_where_when_servlet.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> findByEmail(String username);

    Optional<User> findByUserName(String username);

    List<UserDTO> getAllUserDTOsByRole(Role role);
}
