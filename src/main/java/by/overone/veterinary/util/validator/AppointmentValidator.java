package by.overone.veterinary.util.validator;

import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.util.validator.exception.ValidationException;

public class AppointmentValidator {

    public static boolean validateUserDetails(Appointment appointment) throws ValidationException {
        return validateUserId(appointment.getUsers_user_id()) && validateDoctorId(appointment.getUsers_doctor_id())
                && validatePetId(appointment.getPet_id()) && validateReason(appointment.getReason())
                && validateDiagnosis(appointment.getDiagnosis());
    }

    public static boolean validateUserId(long users_user_id) throws ValidationException {
        if (users_user_id != 0) {
            if (users_user_id > 0) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of user id");
            }
        }
        return true;
    }

    public static boolean validateDoctorId(long users_doctor_id) throws ValidationException {
        if (users_doctor_id != 0) {
            if (users_doctor_id > 0) {
                return true;
            } else {
                throw new ValidationException("Incorrect format doctor id");
            }
        }
        return true;
    }

    public static boolean validatePetId(long pet_id) throws ValidationException {
        if (pet_id != 0) {
            if (pet_id > 0) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of pet id");
            }
        }
        return true;
    }

    public static boolean validateReason(String reason) throws ValidationException {
        if (reason != null) {
            if (!reason.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of reason");
            }
        }
        return true;
    }

    public static boolean validateDiagnosis(String diagnosis) throws ValidationException {
        if (diagnosis != null) {
            if (!diagnosis.isBlank()) {
                return true;
            } else {
                throw new ValidationException("Incorrect format of reason");
            }
        }
        return true;
    }
}
