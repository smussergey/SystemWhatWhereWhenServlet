package ua.training.game.web.command.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {
    private static final Logger LOGGER = Logger.getLogger(ValidationUtil.class);

    public static boolean checkRegisterFields(HttpServletRequest request, String nameUa, String nameEn, String email, String password) {
        boolean isCorrect = true;

        if (!InputValidator.checkNameEn(nameEn)) {
            request.setAttribute("nameEnError", true);
            isCorrect = false;
        }
        if (!InputValidator.checkNameUa(nameUa)) {
            request.setAttribute("nameUaError", true);
            isCorrect = false;
        }
        if (!InputValidator.checkEmail(email)) {
            request.setAttribute("emailError", true);
            isCorrect = false;
        }
        if (!InputValidator.checkPassword(password)) {
            request.setAttribute("passwordError", true);
            isCorrect = false;
        }
        return isCorrect;
    }
}
