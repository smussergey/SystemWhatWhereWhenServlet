package ua.training.game.web.command;

import org.apache.log4j.Logger;
import ua.training.game.web.dto.UserRegistrationDTO;
import ua.training.game.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

   private UserService userService = new UserService();

    public RegistrationCommand() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String nameUa = request.getParameter("nameua");
        String nameEn = request.getParameter("nameen");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LOGGER.info(String.format("In RegistrationCommand, Registration data: nameUa = %S, nameEn = %S, " +
                "username = %S, password = %S", nameUa, nameEn, username, password));
        // TODO validatiion

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setNameUa(nameUa);
        userRegistrationDTO.setNameEn(nameEn);
        userRegistrationDTO.setEmail(username);
        userRegistrationDTO.setPassword(password);

        userService.registerNewUser(userRegistrationDTO);
        return "/login.jsp";

    }
}