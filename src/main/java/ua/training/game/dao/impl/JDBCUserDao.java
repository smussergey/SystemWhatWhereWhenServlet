
package ua.training.game.dao.impl;

import org.apache.log4j.Logger;
import ua.training.game.dao.UserDao;
import ua.training.game.dao.connection.ConnectionPoolHolder;
import ua.training.game.dao.mapper.UserDTOMapper;
import ua.training.game.dao.mapper.UserMapper;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.web.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDao.class);
    private static final String CREATE_USER_QUERY = "INSERT INTO user (name_ua, name_en, email, password, role ) VALUES (?,?,?,?,?)";
    private static final String GET_ALL_USERS_DTOS_BY_ROLE_QUERY = "SELECT user_id, name_en, name_ua FROM user WHERE user.role = ?";

    public JDBCUserDao() {
    }

    @Override
    public void create(User user) {
        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getNameUa());
            ps.setString(2, user.getNameEn());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().name());

            ps.executeUpdate();
            LOGGER.info("In create method: user was created");
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE user_id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            UserMapper userMapper = new UserMapper();

            if (rs.next()) {
                result = Optional.of(userMapper.extractFromResultSet(rs));
            }

            LOGGER.info("In findById method:");
            return result;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new EntityNotFoundException("not found"); // TODO correct
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE email = ?")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            UserMapper mapper = new UserMapper();

            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }

            LOGGER.info("In create findById method:");
            return result;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new EntityNotFoundException("not found"); // TODO correct
        }
    }


    @Override
    public Optional<User> findByUserName(String userName) {
        return findByEmail(userName);
    }

    @Override
    public List<UserDTO> getAllUserDTOsByRole(Role role) {
        List<UserDTO> userDTOs = new ArrayList<>();

        try (Connection connection = ConnectionPoolHolder.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS_DTOS_BY_ROLE_QUERY)) {

            ps.setString(1, role.name());
            ResultSet rs = ps.executeQuery();
            UserDTOMapper userDTOMapper = new UserDTOMapper();

            while (rs.next()) {
                UserDTO userDTO = userDTOMapper
                        .extractFromResultSet(rs);
                userDTOs.add(userDTO);
            }
            return userDTOs;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex); //TODO check
        }
    }


    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Method is not implemented yet");
    }
}

