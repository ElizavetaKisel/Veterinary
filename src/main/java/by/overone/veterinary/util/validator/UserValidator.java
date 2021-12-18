package by.overone.veterinary.util.validator;

import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.util.validator.exception.ValidationException;

public class UserValidator {

    private final static String LOGIN_REGEX = "^[\\w]{4,12}$";
    private final static String EMAIL_REGEX = "^[^\\s]+@[\\w]+\\.[a-z]+$";
    private final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[A-Z]).{6,}$";

    public static boolean validateRegistrationData(UserRegistrationDTO user) throws ValidationException {
        return validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword());
    }

    public static boolean validateLogin(String login) throws ValidationException {
        if (login != null && !login.isBlank() && login.matches(LOGIN_REGEX)) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of login");
        }
    }

    public static boolean validateEmail(String email) throws ValidationException {
        if (email != null && !email.isBlank() && email.matches(EMAIL_REGEX)) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of email");
        }
    }

    public static boolean validatePassword(String password) throws ValidationException {
        if (password != null && !password.isBlank() && password.matches(PASSWORD_REGEX)) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of password");
        }
    }

}
