package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.AppointmentDAO;
import by.overone.veterinary.dto.AppointmentMakeDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@EnableScheduling
public class AppointmentDAOImpl implements AppointmentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Appointment> getAppointmentsByParams(Appointment appointment) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        criteriaQuery.where(criteriaBuilder.notEqual(appointmentRoot.get("status"), Status.DELETED));

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
            predicates.add(criteriaBuilder.equal(appointmentRoot.get("status"), appointment.getStatus()));
        }
        criteriaQuery.select(appointmentRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<Appointment> getAppointmentById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> appointmentRoot = criteriaQuery.from(Appointment.class);
        criteriaQuery.where(criteriaBuilder.equal(appointmentRoot.get("id"), id),
                criteriaBuilder.notEqual(appointmentRoot.get("status"), Status.DELETED));
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
    public Appointment makeAppointment(long id, AppointmentMakeDTO appointmentMakeDTO) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        appointment.setUser(entityManager.find(User.class, appointmentMakeDTO.getUserId()));
        appointment.setPet(entityManager.find(Pet.class, appointmentMakeDTO.getPetId()));
        appointment.setReason(appointmentMakeDTO.getReason());
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

    @Transactional
//    @Scheduled(fixedRate=60*60*1000, initialDelay=10*60*1000)
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
        appointment.setReason(null);
        appointment.setStatus(Status.NEW);
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(long doctorId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Join <Appointment, User> join = criteriaQuery.from(Appointment.class).join("doctor");
        criteriaQuery.where(criteriaBuilder.equal(join.get("id"), doctorId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
