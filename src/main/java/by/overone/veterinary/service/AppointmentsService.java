package by.overone.veterinary.service;

import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;

import java.util.List;

public interface AppointmentsService {

    List<Appointment> getAppointments();

    AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO);

    Appointment updateAppointment(long id, Appointment appointment);

    Appointment getAppointmentById(long id);

    List<Appointment> getAppointmentsByUserId(long users_user_id);

    List<Appointment> getAppointmentsByDoctorId(long users_doctor_id);

    List<Appointment> getAppointmentsByPetId(long pet_id);
}
