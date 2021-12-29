package by.overone.veterinary.util.validator;

import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.util.validator.exception.ValidationException;

public class UserDetailsValidator {

    private final static String PHONE_REGEX = "^(\\+375|80)(17|29|33|44)[0-9]{7}$";
    private final static String NAME_REGEX = "^[А-Л]{1}[a-zа-я]*$";
    private final static String SURNAME_REGEX = "^[A-ZА-Я].*$";

    public static boolean validateUserDetails(UserDetails user) throws ValidationException {
        return validateName(user.getName()) && validateSurname(user.getSurname())
                && validatePhoneNumber(user.getPhone_number()) && validateAddress(user.getAddress());
    }

    public static boolean validatePhoneNumber(String phone_number) throws ValidationException {
        if (phone_number != null) {
            if (!phone_number.isBlank() && phone_number.matches(PHONE_REGEX)) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of phone number");
            }
        }
        return true;
    }

    public static boolean validateAddress(String address) throws ValidationException {
        if (address != null) {
            if (!address.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of address");
            }
        }
        return true;
    }

    public static boolean validateName(String name) throws ValidationException {
        if (name != null) {
            if (!name.isBlank() && name.matches(NAME_REGEX)) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of name");
            }
        }
        return true;
    }

    public static boolean validateSurname(String surname) throws ValidationException {
        if (surname != null) {
            if (!surname.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of surname");
            }
        }
        return true;
    }
}
