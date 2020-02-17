package ua.training.game.web.command.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private final static String REGEX_NAME_UA = "^[А-ЩЬЮЯҐІЇЄ]{1}[а-щьюяґіїє']+$"; //TODO move to properties
    private final static String REGEX_NAME_EN = "^[A-Z]{1}[a-z]+$";//TODO move to properties
    private final static String REGEX_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private final static String REGEX_PASSWORD = "^\\w{0,}$";

    public static boolean checkNameUa(String nameUa) {
        Pattern pattern = Pattern.compile(REGEX_NAME_UA);
        Matcher matcher = pattern.matcher(nameUa);
        return matcher.matches();
    }

    public static boolean checkNameEn(String nameEn) {
        Pattern pattern = Pattern.compile(REGEX_NAME_EN);
        Matcher matcher = pattern.matcher(nameEn);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
