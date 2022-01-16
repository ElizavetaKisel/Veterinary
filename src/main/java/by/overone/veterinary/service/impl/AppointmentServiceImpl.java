package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO;

    @Override
    public List<Appointment> getAppointments() {
        List<Appointment> appointments =  appointmentDAO.getAppointments();
       return appointments;
    }

    @Override
    public AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO) {
        return appointmentDAO.addAppointment(appointmentActiveDTO);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        getAppointmentById(appointment.getAppointment_id());
        return appointmentDAO.updateAppointment(appointment);
    }

    @Override
    public Appointment getAppointmentById(long id) {
        Appointment appointment = appointmentDAO.getAppointmentById(id);
        return appointment;
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
