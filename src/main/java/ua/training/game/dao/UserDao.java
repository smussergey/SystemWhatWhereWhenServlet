package ua.training.game.dao;

import ua.training.game.web.dto.UserDTO;
import ua.training.game.enums.Role;
import ua.training.game.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> findByEmail(String username);

    Optional<User> findByUserName(String username);

    List<UserDTO> getAllUserDTOsByRole(Role role);
}
