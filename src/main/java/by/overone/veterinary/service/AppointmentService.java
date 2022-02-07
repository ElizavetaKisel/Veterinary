package by.overone.veterinary.service;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.model.Appointment;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDataDTO> getAppointments();

    List<AppointmentDataDTO> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO);

    AppointmentDataDTO getAppointmentById(long id);

    AppointmentDataDTO addAppointment(AppointmentNewDTO appointmentNewDTO);

    AppointmentDataDTO updateAppointment(long id, AppointmentNewDTO appointmentNewDTO);

    AppointmentDataDTO deleteAppointment(long id);

    AppointmentDataDTO makeAppointment(long userId, long id, long petId, String reason);

    AppointmentDataDTO closeAppointment(long id, String diagnosis);

    AppointmentDataDTO returnAppointment(long id);

    List<Appointment> getAppointmentsByUserId(long users_user_id);

    List<Appointment> getAppointmentsByDoctorId(long users_doctor_id);

    List<Appointment> getAppointmentsByPetId(long pet_id);
}
