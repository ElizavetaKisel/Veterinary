package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AppointmentDAOImpl implements AppointmentDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_APPOINTMENTS_QUERY = "SELECT * FROM appointments";
    private final static String GET_APPOINTMENT_BY_ID_QUERY = "SELECT * FROM appointments WHERE appointment_id=?";
    private final static String GET_APPOINTMENT_BY_DOCTOR_ID_QUERY = "SELECT * FROM appointments WHERE users_doctor_id=?";
    private final static String GET_APPOINTMENT_BY_USER_ID_QUERY = "SELECT * FROM appointments WHERE users_user_id=?";
    private final static String GET_APPOINTMENT_BY_PET_ID_QUERY = "SELECT * FROM appointments WHERE pet_id=?";
    private final static String ADD_APPOINTMENTS_QUERY = "INSERT INTO appointments VALUE (0, ?, ?, ?, ?)";

    @Override
    public List<Appointment> getAppointments() {
        return jdbcTemplate.query(GET_APPOINTMENTS_QUERY, new BeanPropertyRowMapper<>(Appointment.class));
    }

    @Override
    public AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO) {
        Object[] params = new Object[]{
                appointmentActiveDTO.getUsers_doctor_id(),
                appointmentActiveDTO.getUsers_user_id(),
                appointmentActiveDTO.getPet_id(),
                appointmentActiveDTO.getReason()
        };
        jdbcTemplate.update(ADD_APPOINTMENTS_QUERY, params);
        return appointmentActiveDTO;
    }

    private final static String START_UPDATE_APPOINTMENT_QUERY = "UPDATE appointments SET ";
    private final static String END_UPDATE_APPOINTMENT_QUERY = " WHERE appointment_id=?";
    @Override
    public Appointment updateAppointment(long id, Appointment appointment) {
        if (appointment.getUsers_doctor_id() != 0) {
            jdbcTemplate.update(START_UPDATE_APPOINTMENT_QUERY + "users_doctor_id=?" + END_UPDATE_APPOINTMENT_QUERY, appointment.getUsers_doctor_id(), id);
        }
        if (appointment.getUsers_user_id() != 0) {
            jdbcTemplate.update(START_UPDATE_APPOINTMENT_QUERY + "users_user_id=?" + END_UPDATE_APPOINTMENT_QUERY, appointment.getUsers_user_id(), id);
        }
        if (appointment.getPet_id() != 0) {
            jdbcTemplate.update(START_UPDATE_APPOINTMENT_QUERY + "pet_id=?" + END_UPDATE_APPOINTMENT_QUERY, appointment.getPet_id(), id);
        }
        if (appointment.getReason() != null) {
            jdbcTemplate.update(START_UPDATE_APPOINTMENT_QUERY + "reason=?" + END_UPDATE_APPOINTMENT_QUERY, appointment.getReason(), id);
        }
        if (appointment.getReason() != null) {
            jdbcTemplate.update(START_UPDATE_APPOINTMENT_QUERY + "diagnosis=?" + END_UPDATE_APPOINTMENT_QUERY, appointment.getReason(), id);
        }

        return jdbcTemplate.queryForObject(GET_APPOINTMENT_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Appointment.class));
    }

    @Override
    public Appointment getAppointmentById(long id) {
        return jdbcTemplate.queryForObject(GET_APPOINTMENT_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Appointment.class));
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(long users_user_id) {
        return jdbcTemplate.query(GET_APPOINTMENT_BY_USER_ID_QUERY, new Object[]{users_user_id}, new BeanPropertyRowMapper<>(Appointment.class));
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(long users_doctor_id) {
        return jdbcTemplate.query(GET_APPOINTMENT_BY_DOCTOR_ID_QUERY, new Object[]{users_doctor_id}, new BeanPropertyRowMapper<>(Appointment.class));
    }

    @Override
    public List<Appointment> getAppointmentsByPetId(long pet_id) {
        return jdbcTemplate.query(GET_APPOINTMENT_BY_PET_ID_QUERY, new Object[]{pet_id}, new BeanPropertyRowMapper<>(Appointment.class));
    }
}
