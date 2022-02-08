package by.overone.veterinary.util.mapper;


import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.*;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyMapper{

    private final UserDAO userDAO;

    public AppointmentDataDTO appointmentToDTO(Appointment appointment){
        AppointmentDataDTO appointmentDataDTO = new AppointmentDataDTO();
        appointmentDataDTO.setId(appointment.getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime());
        appointmentDataDTO.setDoctorId(appointment.getDoctor().getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime());
        if (appointment.getUser() != null){
            appointmentDataDTO.setUserId(appointment.getUser().getId());
        }else {
            appointmentDataDTO.setUserId(null);
        }
        if (appointment.getPet() != null){
            appointmentDataDTO.setPetId(appointment.getPet().getId());
        }else {
            appointmentDataDTO.setPetId(null);
        }
        appointmentDataDTO.setReason(appointment.getReason());
        appointmentDataDTO.setDiagnosis(appointment.getDiagnosis());
        return appointmentDataDTO;
    }

    public Appointment newDTOToAppointment(AppointmentNewDTO appointmentNewDTO){
        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentNewDTO.getDateTime());
        appointment.setDoctor(userDAO.getUserById(appointmentNewDTO.getDoctorId())
                .filter(user -> user.getRole().equals(Role.DOCTOR))
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_DOCTOR)));
        return appointment;
    }
    public UserDataDTO userToDataDTO(User user){
        return new UserDataDTO(user.getLogin(),user.getEmail(),user.getRole().toString());
    }

    public UserInfoDTO userToInfoDTO(User user){
        return new UserInfoDTO(
                user.getLogin(),
                user.getEmail(),
                user.getRole().toString(),
                user.getUserDetails().getName(),
                user.getUserDetails().getSurname(),
                user.getUserDetails().getAddress(),
                user.getUserDetails().getPhoneNumber());
    }

    public PetDataDTO petToDTO(Pet pet){
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
