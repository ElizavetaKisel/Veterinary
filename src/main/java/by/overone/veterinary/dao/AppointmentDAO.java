package by.overone.veterinary.dao;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentMakeDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {

    List<Appointment> getAppointmentsByParams(Appointment appointment);

    Optional<Appointment> getAppointmentById(long id);

    Appointment addAppointment(Appointment appointment);

    Appointment updateAppointment(long id, Appointment appointment);

    Appointment deleteAppointment(long id);

    Appointment makeAppointment(long id, AppointmentMakeDTO appointmentMakeDTO);

    Appointment closeAppointment(long id, String diagnosis);

    void autoCloseAppointment();

    Appointment returnAppointment(long id);

}
