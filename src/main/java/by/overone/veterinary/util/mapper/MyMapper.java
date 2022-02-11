package by.overone.veterinary.util.mapper;


import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.*;
import by.overone.veterinary.controller.exception.EntityNotFoundException;
import by.overone.veterinary.controller.exception.ExceptionCode;
import by.overone.veterinary.controller.exception.MyValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyMapper {

    private final UserDAO userDAO;
    private final PetDAO petDAO;

    public AppointmentDataDTO appointmentToDTO(Appointment appointment) {
        AppointmentDataDTO appointmentDataDTO = new AppointmentDataDTO();
        appointmentDataDTO.setId(appointment.getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime().toString().replace("T", " "));
        appointmentDataDTO.setDoctorId(appointment.getDoctor().getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime().toString().replace("T", " "));
        if (appointment.getUser() != null) {
            appointmentDataDTO.setUserId(appointment.getUser().getId());
        } else {
            appointmentDataDTO.setUserId(null);
        }
        if (appointment.getPet() != null) {
            appointmentDataDTO.setPetId(appointment.getPet().getId());
        } else {
            appointmentDataDTO.setPetId(null);
        }
        appointmentDataDTO.setReason(appointment.getReason());
        appointmentDataDTO.setDiagnosis(appointment.getDiagnosis());
        if (appointment.getStatus() != null) {
            appointmentDataDTO.setStatus(appointment.getStatus().toString());
        }
        return appointmentDataDTO;
    }

    public Appointment newDTOToAppointment(AppointmentNewDTO appointmentNewDTO) {
        Appointment appointment = new Appointment();
        if (appointmentNewDTO.getDateTime().isBefore(LocalDateTime.now())){
            throw new MyValidationException(ExceptionCode.WRONG_DATE);
        }
        if (appointmentNewDTO.getDateTime().getHour() > 19 || appointmentNewDTO.getDateTime().getHour() < 9) {
            throw new MyValidationException(ExceptionCode.WRONG_TIME);
        } else {
            appointment.setDateTime(appointmentNewDTO.getDateTime());
        }
        if (appointmentNewDTO.getDoctorId() != null) {
            appointment.setDoctor(userDAO.getUserById(appointmentNewDTO.getDoctorId())
                    .filter(user -> user.getRole().equals(Role.DOCTOR))
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_DOCTOR)));
        } else {
            appointment.setDoctor(null);
        }
        return appointment;

    }

    public Appointment dtoToAppointment(AppointmentDataDTO appointmentDataDTO) {
        Appointment appointment = new Appointment();
        if (appointmentDataDTO.getDoctorId() != null) {
            appointment.setDoctor(userDAO.getUserById(appointmentDataDTO.getDoctorId())
                    .filter(user -> user.getRole().equals(Role.DOCTOR))
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_DOCTOR)));
        } else {
            appointment.setDoctor(null);
        }
        if (appointmentDataDTO.getUserId() != null) {
            appointment.setUser(userDAO.getUserById(appointmentDataDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER)));
        } else {
            appointment.setUser(null);
        }
        if (appointmentDataDTO.getPetId() != null) {
            appointment.setPet(petDAO.getPetById(appointmentDataDTO.getPetId())
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_PET)));
        } else {
            appointment.setPet(null);
        }
        appointment.setReason(appointmentDataDTO.getReason());
        appointment.setDiagnosis(appointmentDataDTO.getDiagnosis());
        if (appointmentDataDTO.getStatus() != null) {
            if (EnumUtils.isValidEnum(Status.class, appointmentDataDTO.getStatus().toUpperCase())) {
                appointment.setStatus(Status.valueOf(appointmentDataDTO.getStatus().toUpperCase()));
            }
        }
        return appointment;
    }

    public UserDataDTO userToDataDTO(User user) {
        return new UserDataDTO(user.getLogin(), user.getEmail(), user.getRole().toString());
    }

    public UserInfoDTO userToInfoDTO(User user) {
        return new UserInfoDTO(
                user.getLogin(),
                user.getEmail(),
                user.getRole().toString(),
                user.getUserDetails().getName(),
                user.getUserDetails().getSurname(),
                user.getUserDetails().getAddress(),
                user.getUserDetails().getPhoneNumber());
    }

    public PetDataDTO petToDTO(Pet pet) {
        return new PetDataDTO(
                pet.getName(),
                pet.getType(),
                pet.getBreed(),
                pet.getAge(),
                pet.getOwners().stream().map(User::getId).collect(Collectors.toList()));
    }

    public Pet dtoToPet(PetDataDTO petDataDTO) {
        Pet pet = new Pet();
        pet.setName(petDataDTO.getName());
        pet.setType(petDataDTO.getType());
        pet.setBreed(petDataDTO.getBreed());
        pet.setAge(petDataDTO.getAge());
        pet.setOwners(petDataDTO.getOwners().stream().map(o -> userDAO.getUserById(o)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER))).collect(Collectors.toList()));
        return pet;
    }
}
