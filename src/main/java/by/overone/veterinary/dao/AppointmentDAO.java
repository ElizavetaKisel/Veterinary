package by.overone.veterinary.dao;

import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;

import java.util.List;

public interface AppointmentDAO {
    
    List<Appointment> getAppointments();

    AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO);

    Appointment updateAppointment(Appointment appointment);

    Appointment getAppointmentById(long id);

    List<Appointment> getAppointmentsByUserId(long users_user_id);

    List<Appointment> getAppointmentsByDoctorId(long users_doctor_id);

    List<Appointment> getAppointmentsByPetId(long pet_id);
}
