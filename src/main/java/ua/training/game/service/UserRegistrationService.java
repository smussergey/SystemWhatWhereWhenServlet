//package ua.training.system_what_where_when_servlet.service;
//
//public class UserRegistrationService {
//
////    private static final Logger LOGGER = Logger.getLogger(UserRegistrationService.class);
//
//    private final UserService userService = new UserService();
//
//
//
//}

//    private DaoFactory daoFactory;
//
//    UserRegistrationService() {
//        this.daoFactory = DaoFactory.getInstance();
//    }
//
//    public void register(UserRegistrationDTO userRegistrationDto) {
//        User user = new User();
//        user.setNameUa(userRegistrationDto.getNameUa());
//        user.setNameEn(userRegistrationDto.getNameEn());
//        user.setEmail(userRegistrationDto.getEmail());
//        user.setPassword(BCrypt.hashpw(userRegistrationDto.getPassword(), BCrypt.gensalt()));
//        user.setRole(Role.ROLE_PLAYER);
//
//        UserDao userDao = daoFactory.createUserDao();
//        userDao.create(user);
//    }


