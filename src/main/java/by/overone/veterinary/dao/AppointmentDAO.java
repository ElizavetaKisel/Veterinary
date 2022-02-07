package by.overone.veterinary.dao;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {
    
    List<Appointment> getAppointments();

    List<Appointment> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO);

    Optional<Appointment> getAppointmentById(long id);

    Appointment addAppointment(Appointment appointment);

    Appointment updateAppointment(long id, AppointmentNewDTO appointmentNewDTO);

    Appointment deleteAppointment(long id);

    Appointment makeAppointment(long userId, long id, long petId, String reason);

    Appointment closeAppointment(long id, String diagnosis);

    Appointment returnAppointment(long id);

    List<Appointment> getAppointmentsByUserId(long users_user_id);

    List<Appointment> getAppointmentsByDoctorId(long users_doctor_id);

    List<Appointment> getAppointmentsByPetId(long pet_id);
}
