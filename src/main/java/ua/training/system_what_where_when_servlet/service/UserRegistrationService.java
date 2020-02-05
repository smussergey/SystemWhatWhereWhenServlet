package ua.training.system_what_where_when_servlet.service;

import org.mindrot.jbcrypt.BCrypt;
import ua.training.system_what_where_when_servlet.dao.DaoFactory;
import ua.training.system_what_where_when_servlet.dao.UserDao;
import ua.training.system_what_where_when_servlet.dto.UserRegistrationDTO;
import ua.training.system_what_where_when_servlet.entity.Role;
import ua.training.system_what_where_when_servlet.entity.User;

public class UserRegistrationService {

    private DaoFactory daoFactory;

    UserRegistrationService() {
        this.daoFactory = DaoFactory.getInstance();
    }

    public void register(UserRegistrationDTO userRegistrationDto) {
        User user = new User();
        user.setNameUa(userRegistrationDto.getNameUa());
        user.setNameEn(userRegistrationDto.getNameEn());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(BCrypt.hashpw(userRegistrationDto.getPassword(), BCrypt.gensalt()));
        user.setRole(Role.ROLE_PLAYER);

        UserDao userDao = daoFactory.createUserDao();
        userDao.create(user);
    }

}
