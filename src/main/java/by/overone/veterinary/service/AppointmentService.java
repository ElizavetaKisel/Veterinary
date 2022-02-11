package by.overone.veterinary.service;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentMakeDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDataDTO> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO);

    AppointmentDataDTO getAppointmentById(long id);

    AppointmentDataDTO addAppointment(AppointmentNewDTO appointmentNewDTO);

    AppointmentDataDTO updateAppointment(long id, AppointmentNewDTO appointmentNewDTO);

    AppointmentDataDTO deleteAppointment(long id);

    AppointmentDataDTO makeAppointment(long id, AppointmentMakeDTO appointmentMakeDTO);

    AppointmentDataDTO closeAppointment(long id, String diagnosis);

    AppointmentDataDTO returnAppointment(long id);

    List<AppointmentDataDTO> getAppointmentsByDoctorId(long doctorId);
}
