package ua.training.game.service;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import ua.training.game.dao.DaoFactory;
import ua.training.game.dao.UserDao;
import ua.training.game.web.dto.UserDTO;
import ua.training.game.web.dto.UserRegistrationDTO;
import ua.training.game.enums.Role;
import ua.training.game.domain.User;
import ua.training.game.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public void registerNewUser(UserRegistrationDTO userRegistrationDto) {
        User user = new User();
        user.setNameUa(userRegistrationDto.getNameUa());
        user.setNameEn(userRegistrationDto.getNameEn());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(BCrypt.hashpw(userRegistrationDto.getPassword(), BCrypt.gensalt()));
        user.setRole(Role.ROLE_PLAYER);

        save(user);
    }

//
//    public User findUserByLogin(String email) {
//        User user = userRepository
//                .findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " was not found"));
//        log.info("User with login {} is trying to log in ", user.getEmail());
//        return user;
//    }

//    public User findUserById(Long id) {
//        return userRepository
//                .findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("User with " + id + " was not found"));
//    }


    public User findUserById(int id) {
        UserDao userDao = daoFactory.createUserDao();
        return userDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", id)));
    }

//    public List<User> findAllUsersByRole(Role role) {
//        return userRepository.findByRole(role);
//    }

    public List<UserDTO> getAllUserDTOsByRole(Role role) {
        List<UserDTO> userDTOs = new ArrayList<>();
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDTOs = userDao.getAllUserDTOsByRole(role);
        } catch (Exception e) {
            e.printStackTrace(); //TODO Correct
        }
        return userDTOs;
    }


//    @Transactional
//    public User save(User user) {
//        return userRepository.save(user);
//    }
//


    public void save(User user) {
        LOGGER.info("In method save user : " + user.getEmail());
        daoFactory.createUserDao().create(user);
    }
//        LOGGER.info(String.format("User with email %s was saved", user.getEmail()));
//        return 0;


//    public User findLoggedIndUser() { //TODO redo using Principal
//        String username = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//        return findUserByLogin(username);
//    }
//}


    //
//    public Optional<User> findById(int id) {
//        Optional<User> userOptional = Optional.empty();
//
//        try (UserDao userDao = daoFactory.createUserDao()) {
//            userOptional = userDao.findById(id);
//        }
//        catch (Exception e) {
//            e.printStackTrace(); //TODO Correct
//        }
//        return userOptional;
//    }
//
    public User findByUsername(String username) {
        UserDao userDao = daoFactory.createUserDao();
        return userDao.findByUserName(username).orElseThrow(() -> new EntityNotFoundException(String.format("User with username: %s not found", username)));
    }


    public User findByUsernameAndPassword(String username, String password) {
        UserDao userDao = daoFactory.createUserDao();
        User user = userDao.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with username: %s not found", username)));

        if (isPasswordValid(user, password)) {
            LOGGER.info(String.format("Password %s is valid", password));
            return user;
        } else throw new EntityNotFoundException(String.format("User with username: %s not found", username));
    }

    private boolean isPasswordValid(User user, String password) {
        LOGGER.info(String.format("User password is: %s", user.getPassword()));
        return BCrypt.checkpw(password, user.getPassword());
    }


}