package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;


@Repository
@RequiredArgsConstructor
//@EnableScheduling
public class AppointmentDAOImpl implements AppointmentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Appointment> getAppointments() {
        autoCloseAppointment();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        criteriaQuery.from(Appointment.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Appointment> getAppointmentsByParams(Appointment appointment) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();
        if (appointment.getDoctor() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("doctor"), appointment.getDoctor()));
        }
        if (appointment.getUser() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("user"), appointment.getUser()));
        }
        if (appointment.getPet() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("pet"), appointment.getPet()));
        }
        if (appointment.getReason() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("reason"), appointment.getReason()));
        }
        if (appointment.getDiagnosis() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("diagnosis"), appointment.getDiagnosis()));
        }
        if (appointment.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("status"), Status.valueOf(appointment.getDiagnosis().toUpperCase())));
        }
        criteriaQuery.select(appointmentRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<Appointment> getAppointmentById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        criteriaQuery.where(criteriaBuilder.equal(appointmentRoot.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        entityManager.persist(appointment);
        return appointment;
    }

    @Override
    public Appointment updateAppointment(long id, Appointment appointment) {
        Appointment appointmentDB = entityManager.find(Appointment.class, id);
        if (appointment.getDateTime() != null){
            appointmentDB.setDateTime(appointment.getDateTime());
        }
        if (appointment.getDoctor() != null){
            appointmentDB.setDoctor(appointment.getDoctor());
        }
        return appointmentDB;
    }

    @Override
    public Appointment deleteAppointment(long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setStatus(Status.DELETED);
        return appointment;
    }

    @Override
    public Appointment makeAppointment(long userId, long id, long petId, String reason) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setUser(entityManager.find(User.class, userId));
        appointment.setPet(entityManager.find(Pet.class, petId));
        appointment.setReason(reason);
        appointment.setStatus(Status.ACTIVE);
        return appointment;
    }

    @Override
    public Appointment closeAppointment(long id, String diagnosis) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setDiagnosis(diagnosis);
        appointment.setStatus(Status.CLOSED);
        return appointment;
    }

//    @Scheduled(fixedRate=500000)
    public void autoCloseAppointment() {
        LocalDateTime current = now();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        criteriaQuery.from(Appointment.class);
        entityManager.createQuery(criteriaQuery).getResultList().stream()
        .filter(appointment -> appointment.getDateTime().isBefore(current))
        .forEach(appointment -> appointment.setStatus(Status.CLOSED));
    }

    @Override
    public Appointment returnAppointment(long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setUser(null);
        appointment.setPet(null);
        appointment.setStatus(Status.NEW);
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(long userId) {
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(long doctorId) {
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsByPetId(long petId) {
        return null;
    }


//    private final JdbcTemplate jdbcTemplate;
//
//    private static final String GET_APPOINTMENTS_QUERY = "SELECT * FROM appointments";
//    private final static String GET_APPOINTMENT_BY_ID_QUERY = "SELECT * FROM appointments WHERE appointment_id=?";
//    private final static String GET_APPOINTMENT_BY_DOCTOR_ID_QUERY = "SELECT * FROM appointments WHERE appointments_doctor_id=?";
//    private final static String GET_APPOINTMENT_BY_USER_ID_QUERY = "SELECT * FROM appointments WHERE appointments_appointment_id=?";
//    private final static String GET_APPOINTMENT_BY_PET_ID_QUERY = "SELECT * FROM appointments WHERE pet_id=?";
//    private final static String ADD_APPOINTMENTS_QUERY = "INSERT INTO appointments (date, time, appointments_doctor_id, appointments_appointment_id, pet_id, reason) " +
//            "VALUE (?, ?, ?, ?, ?, ?)";
//
//    @Override
//    public List<Appointment> getAppointments() {
//        return jdbcTemplate.query(GET_APPOINTMENTS_QUERY, new BeanPropertyRowMapper<>(Appointment.class));
//    }
//
//    @Override
//    public AppointmentActiveDTO addAppointment(AppointmentActiveDTO appointmentActiveDTO) {
//        Object[] params = new Object[]{
//                appointmentActiveDTO.getDate(),
//                appointmentActiveDTO.getTime(),
//                appointmentActiveDTO.getAppointments_doctor_id(),
//                appointmentActiveDTO.getAppointments_appointment_id(),
//                appointmentActiveDTO.getPet_id(),
//                appointmentActiveDTO.getReason()
//        };
//        jdbcTemplate.update(ADD_APPOINTMENTS_QUERY, params);
//        return appointmentActiveDTO;
//    }
//    @Override
//    public Appointment updateAppointment(Appointment appointment) {
//        List <String> sql = new ArrayList<>();
//        if (appointment.getAppointments_doctor_id() != 0) {
//            sql.add("appointments_doctor_id=" + appointment.getAppointments_doctor_id());
//        }
//        if (appointment.getAppointments_appointment_id() != 0) {
//            sql.add("appointments_appointment_id=" + appointment.getAppointments_appointment_id());
//        }
//        if (appointment.getPet_id() != 0) {
//           sql.add("pet_id=" + appointment.getPet_id());
//        }
//        if (appointment.getReason() != null) {
//            sql.add("reason='" + appointment.getReason() + "'");
//        }
//        if (appointment.getDiagnosis() != null) {
//           sql.add("diagnosis='" + appointment.getDiagnosis() + "'");
//        }
//        String UPDATE_APPOINTMENT_QUERY = "UPDATE appointments SET " + sql.stream().collect(Collectors.joining(", "))
//                + " WHERE appointment_id=" + appointment.getAppointment_id();
//        System.out.println(UPDATE_APPOINTMENT_QUERY);
//        jdbcTemplate.update(UPDATE_APPOINTMENT_QUERY);
//        return jdbcTemplate.queryForObject(GET_APPOINTMENT_BY_ID_QUERY, new Object[]{appointment.getAppointment_id()},
//                new BeanPropertyRowMapper<>(Appointment.class));
//    }
//
//    @Override
//    public Appointment getAppointmentById(long id) {
//        return jdbcTemplate.queryForObject(GET_APPOINTMENT_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Appointment.class));
//    }
//
//    @Override
//    public List<Appointment> getAppointmentsByAppointmentId(long appointments_appointment_id) {
//        return jdbcTemplate.query(GET_APPOINTMENT_BY_USER_ID_QUERY, new Object[]{appointments_appointment_id}, new BeanPropertyRowMapper<>(Appointment.class));
//    }
//
//    @Override
//    public List<Appointment> getAppointmentsByDoctorId(long appointments_doctor_id) {
//        return jdbcTemplate.query(GET_APPOINTMENT_BY_DOCTOR_ID_QUERY, new Object[]{appointments_doctor_id}, new BeanPropertyRowMapper<>(Appointment.class));
//    }
//
//    @Override
//    public List<Appointment> getAppointmentsByPetId(long pet_id) {
//        return jdbcTemplate.query(GET_APPOINTMENT_BY_PET_ID_QUERY, new Object[]{pet_id}, new BeanPropertyRowMapper<>(Appointment.class));
//    }
}
