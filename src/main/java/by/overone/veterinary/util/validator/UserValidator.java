package by.overone.veterinary.util.validator;

import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.util.validator.exception.ValidationException;

public class UserValidator {

    private final static String LOGIN_REGEX = "^[\\w]{4,12}$";
    private final static String EMAIL_REGEX = "^[^\\s]+@[\\w]+\\.[a-z]+$";
    private final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[A-Z]).{6,}$";
    private final static String PHONE_REGEX = "^(\\+375|80)(17|29|33|44)[0-9]{7}$";

    public static boolean validateRegistrationData(UserRegistrationDTO user) throws ValidationException {

        if (validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword())) {
            return validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword());
        }else {
            throw new ValidationException("Incorrect format");
        }
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

    public static boolean validateUserDetails(UserDetails user) throws ValidationException {
        if (validatePhoneNumber(user.getPhoneNumber())) {
            return validatePhoneNumber(user.getPhoneNumber());
        }else {
            throw new ValidationException("Incorrect format");
        }
    }
    public static boolean validatePhoneNumber(String phoneNumber) {
        return  phoneNumber != null && !phoneNumber.isBlank() && phoneNumber.matches(PHONE_REGEX);
    }
}
