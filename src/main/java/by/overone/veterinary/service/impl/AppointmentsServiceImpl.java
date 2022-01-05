package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.service.AppointmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentDAO appointmentDAO;

    @Override
    public List<Appointment> getAppointments() {
        return appointmentDAO.getAppointments();
    }

    @Override
    public AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO) {
        return appointmentDAO.addAppointment(appointmentActiveDTO);
    }

    @Override
    public Appointment updateAppointment(long id, Appointment appointment) {
        getAppointmentById(id);
        return appointmentDAO.updateAppointment(id, appointment);
    }

    @Override
    public Appointment getAppointmentById(long id) {
        return appointmentDAO.getAppointmentById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(long users_user_id) {
        return appointmentDAO.getAppointmentsByUserId(users_user_id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(long users_doctor_id) {
        return appointmentDAO.getAppointmentsByDoctorId(users_doctor_id);
    }

    @Override
    public List<Appointment> getAppointmentsByPetId(long pet_id) {
        return appointmentDAO.getAppointmentsByPetId(pet_id);
    }
}
