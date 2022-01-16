package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AppointmentDAOImpl implements AppointmentDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_APPOINTMENTS_QUERY = "SELECT * FROM appointments";
    private final static String GET_APPOINTMENT_BY_ID_QUERY = "SELECT * FROM appointments WHERE appointment_id=?";
    private final static String GET_APPOINTMENT_BY_DOCTOR_ID_QUERY = "SELECT * FROM appointments WHERE users_doctor_id=?";
    private final static String GET_APPOINTMENT_BY_USER_ID_QUERY = "SELECT * FROM appointments WHERE users_user_id=?";
    private final static String GET_APPOINTMENT_BY_PET_ID_QUERY = "SELECT * FROM appointments WHERE pet_id=?";
    private final static String ADD_APPOINTMENTS_QUERY = "INSERT INTO appointments (users_doctor_id, users_user_id, pet_id, reason) " +
            "VALUE (?, ?, ?, ?)";

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
    @Override
    public Appointment updateAppointment(Appointment appointment) {
        List <String> sql = new ArrayList<>();
        if (appointment.getUsers_doctor_id() != 0) {
            sql.add("users_doctor_id=" + appointment.getUsers_doctor_id());
        }
        if (appointment.getUsers_user_id() != 0) {
            sql.add("users_user_id=" + appointment.getUsers_user_id());
        }
        if (appointment.getPet_id() != 0) {
           sql.add("pet_id=" + appointment.getPet_id());
        }
        if (appointment.getReason() != null) {
            sql.add("reason='" + appointment.getReason() + "'");
        }
        if (appointment.getDiagnosis() != null) {
           sql.add("diagnosis='" + appointment.getDiagnosis() + "'");
        }
        String UPDATE_APPOINTMENT_QUERY = "UPDATE appointments SET " + sql.stream().collect(Collectors.joining(", "))
                + " WHERE appointment_id=" + appointment.getAppointment_id();
        System.out.println(UPDATE_APPOINTMENT_QUERY);
        jdbcTemplate.update(UPDATE_APPOINTMENT_QUERY);
        return jdbcTemplate.queryForObject(GET_APPOINTMENT_BY_ID_QUERY, new Object[]{appointment.getAppointment_id()},
                new BeanPropertyRowMapper<>(Appointment.class));
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
