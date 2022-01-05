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

    public static boolean validatePetUpdate(PetDataDTO pet) throws ValidationException {
        return validateNameUpdate(pet.getName()) && validateTypeUpdate(pet.getType())
                && validateBreedUpdate(pet.getBreed()) && validateAgeUpdate(pet.getAge());
    }

    public static boolean validateBreedUpdate(String breed) throws ValidationException {
        if(breed != null) {
            if (!breed.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of breed");
            }
        }
        return true;
    }

    public static boolean validateAgeUpdate(int age) throws ValidationException {
        if(age != 0) {
            if (age > 0) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of age");
            }
        }
        return true;
    }

    public static boolean validateNameUpdate(String name) throws ValidationException {
        if(name != null) {
            if (!name.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of name");
            }
        }
        return true;
    }

    public static boolean validateTypeUpdate(String type) throws ValidationException {
        if(type != null) {
            if (!type.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of type");
            }
        }
        return true;
    }
}
