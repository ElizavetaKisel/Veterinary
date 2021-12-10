package by.overone.veterinary.util.validator;

import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.User;

public class UserValidator {

    private final static String LOGIN_REGEX = "^[\\w]{4,12}$";
    private final static String EMAIL_REGEX = "^[^\\s]+@[\\w]+\\.[a-z]+$";
    private final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[A-Z]).{6,}$";

    public static boolean validateRegistrationData(UserRegistrationDTO user) {
        return validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword());
    }

    public static boolean validateLogin(String login) {
        return login != null && !login.isBlank() && login.matches(LOGIN_REGEX);
    }

    public static boolean validateEmail(String email) {
        return email != null && !email.isBlank() && email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password != null && !password.isBlank() && password.matches(PASSWORD_REGEX);
    }
}
