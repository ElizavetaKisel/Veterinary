package by.overone.veterinary.util.validator;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.util.validator.exception.ValidationException;

public class PetValidator {

    public static boolean validatePet(PetDataDTO pet) throws ValidationException {
        return validateName(pet.getName()) && validateType(pet.getType())
                && validateBreed(pet.getBreed()) && validateAge(pet.getAge());
    }

    public static boolean validateBreed(String breed) throws ValidationException {
        if (breed != null && !breed.isBlank()) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of breed");
        }
    }

    public static boolean validateAge(int age) throws ValidationException {
        if(age > 0) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of age");
        }
    }

    public static boolean validateName(String name) throws ValidationException {
        if (name != null && !name.isBlank()) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of name");
        }
    }

    public static boolean validateType(String type) throws ValidationException {
        if (type != null && !type.isBlank()) {
            return true;
        }else {
            throw new ValidationException("Incorrect format of type");
        }
    }
}
