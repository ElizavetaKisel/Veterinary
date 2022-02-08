package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.exception.EntityAlreadyExistException;
import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.exception.ExceptionCode;
import by.overone.veterinary.model.*;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.util.mapper.MyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final UserDAO userDAO;
    private final PetDAO petDAO;
    private final MyMapper myMapper;

    @Override
    public List<AppointmentDataDTO> getAppointments() {
        List<AppointmentDataDTO> appointmentsDataDTO;
        appointmentsDataDTO = appointmentDAO.getAppointments().stream()
                .map(appointment -> myMapper.appointmentToDTO(appointment))
                .collect(Collectors.toList());

        return appointmentsDataDTO;
    }

    @Override
    public List<AppointmentDataDTO> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        List<AppointmentDataDTO> appointments;
        appointments = appointmentDAO.getAppointmentsByParams(appointmentDataDTO).stream()
                .map(appointment -> myMapper.appointmentToDTO(appointment))
                .collect(Collectors.toList());

        return appointments;
    }

    @Override
    public AppointmentDataDTO getAppointmentById(long id) {
        Appointment appointment = appointmentDAO.getAppointmentById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO addAppointment(AppointmentNewDTO appointmentNewDTO) {
        try{
            Appointment appointment = myMapper.newDTOToPet(appointmentNewDTO);
            appointment.setStatus(Status.NEW);
            return myMapper.appointmentToDTO(appointmentDAO.addAppointment(appointment));
        }catch (PersistenceException e){
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_APPOINTMENT);
        }
    }

    @Override
    public AppointmentDataDTO updateAppointment(long id, AppointmentNewDTO appointmentNewDTO) {
        getAppointmentById(id);
        userDAO.getUserById(appointmentNewDTO.getDoctorId());
        Appointment appointment = appointmentDAO.updateAppointment(id, appointmentNewDTO);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO deleteAppointment(long id) {
        getAppointmentById(id);
        Appointment appointment = appointmentDAO.deleteAppointment(id);
        return  myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO makeAppointment(long userId, long id, long petId, String reason) {
        getAppointmentById(id);
        userDAO.getUserById(userId);
        petDAO.getPetById(petId);
        Appointment appointment = appointmentDAO.makeAppointment(userId, id, petId, reason);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO closeAppointment(long id, String diagnosis) {
        getAppointmentById(id);
        Appointment appointment = appointmentDAO.closeAppointment(id, diagnosis);
        return  myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO returnAppointment(long id) {
        Appointment appointment = appointmentDAO.returnAppointment(id);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(long users_user_id) {
        return null;
    }


    @Override
    public List<Appointment> getAppointmentsByDoctorId(long appointments_doctor_id) {
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByPetId(long pet_id) {
        return null;
    }
}
