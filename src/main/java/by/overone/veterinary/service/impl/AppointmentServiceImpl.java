package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.*;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.service.exception.EntityAlreadyExistException;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.ExceptionCode;
import by.overone.veterinary.model.*;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.service.exception.MyValidationException;
import by.overone.veterinary.util.mapper.MyMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final UserService userService;
    private final PetService petService;
    private final MyMapper myMapper;

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
        if (!getAppointmentById(id).getStatus().equals(Status.NEW.toString())) {
            throw new MyValidationException(ExceptionCode.NOT_NEW_APP);
        }
        Appointment appointment = appointmentDAO.deleteAppointment(id);
        return myMapper.appointmentToDTO(appointment);
    }

    @Override
    public AppointmentDataDTO makeAppointment(long id, AppointmentMakeDTO appointmentMakeDTO) {
        if (EnumUtils.isValidEnum(Status.class, getAppointmentById(id).getStatus())) {
            if (!getAppointmentById(id).getStatus().equals(Status.NEW.toString())) {
                throw new EntityNotFoundException(ExceptionCode.NOT_EXISTING_APPOINTMENT);
            }
        } else {
            throw new MyValidationException(ExceptionCode.WRONG_STATUS);
        }
        if (!userService.getUserById(appointmentMakeDTO.getUserId()).getRole().equals(Role.CUSTOMER.toString())) {
            throw new EntityNotFoundException(ExceptionCode.NOT_EXISTING_CUSTOMER);
        }
        if (!petService.getPetById(appointmentMakeDTO.getPetId()).getOwners().contains(appointmentMakeDTO.getUserId())){
            throw new MyValidationException(ExceptionCode.NOT_PET_OWNER);
        }
        petService.getPetById(appointmentMakeDTO.getPetId());
        Appointment appointment = appointmentDAO.makeAppointment(id, appointmentMakeDTO);
        return myMapper.appointmentToDTO(appointment);

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

}
