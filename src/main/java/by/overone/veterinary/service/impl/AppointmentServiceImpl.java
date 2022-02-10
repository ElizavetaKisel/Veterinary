package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.service.exception.EntityAlreadyExistException;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.ExceptionCode;
import by.overone.veterinary.model.*;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.util.mapper.MyMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ModelMapper modelMapper;

    @Override
    public List<AppointmentDataDTO> getAppointments() {
        List<AppointmentDataDTO> appointmentsDataDTO;
        appointmentsDataDTO = appointmentDAO.getAppointments().stream()
                .map(myMapper::appointmentToDTO)
                .collect(Collectors.toList());

        return appointmentsDataDTO;
    }

    @Override
    public List<AppointmentDataDTO> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        List<AppointmentDataDTO> appointments;
        appointments = appointmentDAO.getAppointmentsByParams(myMapper.dtoToAppointment(appointmentDataDTO)).stream()
                .map(myMapper::appointmentToDTO)
                .collect(Collectors.toList());

        if (appointmentDataDTO.getDateTime() != null) {
            appointments = appointments.stream()
                    .filter(app -> app.getDateTime().contains(appointmentDataDTO.getDateTime()))
                    .collect(Collectors.toList());
        }
        return appointments;
    }

    @Override
    public AppointmentDataDTO getAppointmentById(long id) {
        Appointment appointment = appointmentDAO.getAppointmentById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_APPOINTMENT));
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO addAppointment(AppointmentNewDTO appointmentNewDTO) {
        Appointment appointment = myMapper.newDTOToAppointment(appointmentNewDTO);
        if (!getAppointmentsByParams(myMapper.appointmentToDTO(appointment)).isEmpty()) {
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_APPOINTMENT);
        }
        appointment.setStatus(Status.NEW);
        return myMapper.appointmentToDTO(appointmentDAO.addAppointment(appointment));
    }

    @Override
    public AppointmentDataDTO updateAppointment(long id, AppointmentNewDTO appointmentNewDTO) {
        getAppointmentById(id);
        Appointment appointment = myMapper.newDTOToAppointment(appointmentNewDTO);
        return myMapper.appointmentToDTO(appointmentDAO.updateAppointment(id, appointment));
    }

    @Override
    public AppointmentDataDTO deleteAppointment(long id) {
        getAppointmentById(id);
        Appointment appointment = appointmentDAO.deleteAppointment(id);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO makeAppointment(long userId, long id, long petId, String reason) {
        if (getAppointmentById(id).getStatus().compareTo(Status.NEW.toString()) != 0) {
            userDAO.getUserById(userId);
            petDAO.getPetById(petId);
            Appointment appointment = appointmentDAO.makeAppointment(userId, id, petId, reason);
            return myMapper.appointmentToDTO(appointment);
        } else {
            throw new EntityNotFoundException(ExceptionCode.NOT_EXISTING_APPOINTMENT);
        }
    }

    @Override
    public AppointmentDataDTO closeAppointment(long id, String diagnosis) {
        getAppointmentById(id);
        Appointment appointment = appointmentDAO.closeAppointment(id, diagnosis);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO returnAppointment(long id) {
        Appointment appointment = appointmentDAO.returnAppointment(id);
        return myMapper.appointmentToDTO(appointment);
    }
    

    @Override
    public List<AppointmentDataDTO> getAppointmentsByDoctorId(long doctorId) {
        List<AppointmentDataDTO> appointmentsDataDTO;
        appointmentsDataDTO = appointmentDAO.getAppointmentsByDoctorId(doctorId).stream()
                .map(myMapper::appointmentToDTO)
                .collect(Collectors.toList());

        return appointmentsDataDTO;
    }
}
