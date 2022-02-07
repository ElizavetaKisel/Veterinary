package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AppointmentDAOImpl implements AppointmentDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private UserDAO userDAO;

    @Override
    public List<Appointment> getAppointments() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Appointment> getAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);

        List<Predicate> predicates = new ArrayList<>();

        if (appointmentDataDTO.getDateTime() != null) {
            predicates.add(criteriaBuilder.like(appointmentRoot.get("dateTime"), '%' + appointmentDataDTO.getDateTime().toString() + '%'));
        }
        if (appointmentDataDTO.getDoctorId() != 0) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("doctorId"), appointmentDataDTO.getDoctorId()));
        }
        if (appointmentDataDTO.getUserId() != 0) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("userId"), appointmentDataDTO.getUserId()));
        }
        if (appointmentDataDTO.getPetId() != 0) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("petId"), appointmentDataDTO.getPetId()));
        }
        if (appointmentDataDTO.getReason() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("reason"), appointmentDataDTO.getReason()));
        }
        if (appointmentDataDTO.getDiagnosis() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("diagnosis"), appointmentDataDTO.getDiagnosis()));
        }
        if (appointmentDataDTO.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("status"), Status.valueOf(appointmentDataDTO.getDiagnosis().toUpperCase())));
        }
        criteriaQuery.select(appointmentRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
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
    public Appointment updateAppointment(long id, AppointmentNewDTO appointmentNewDTO) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        if (appointmentNewDTO.getDateTime() != null){
            appointment.setDateTime(appointmentNewDTO.getDateTime());
        }
        if (appointmentNewDTO.getDoctorId() != 0){
            appointment.setDoctor(entityManager.find(User.class, appointmentNewDTO.getDoctorId()));
        }
//        if (appointmentClosedDTO.getUserId() != 0){
//            appointment.setUser(entityManager.find(User.class, appointmentClosedDTO.getUserId()));
//        }
//        if (appointmentClosedDTO.getPetId() != 0){
//            appointment.setPet(entityManager.find(Pet.class, appointmentClosedDTO.getPetId()));
//        }
//        if (appointmentClosedDTO.getReason() != null){
//            appointment.setReason(appointmentClosedDTO.getReason());
//        }
//        if (appointmentClosedDTO.getDiagnosis() != null){
//            appointment.setDiagnosis(appointmentClosedDTO.getDiagnosis());
//        }
        return appointment;
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
