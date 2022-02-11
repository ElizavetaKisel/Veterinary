package by.overone.veterinary.dao.impl;


import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.*;
import by.overone.veterinary.service.exception.ExceptionCode;
import by.overone.veterinary.service.exception.MyValidationException;
import by.overone.veterinary.service.exception.UpdateException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.notEqual(userRoot.get("status"), Status.DELETED));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<User> getUsersByParams(UserInfoDTO userInfoDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, UserDetails> join = userRoot.join("userDetails");

        List<Predicate> predicates = new ArrayList<>();

        if (userInfoDTO.getLogin() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("login"), '%' + userInfoDTO.getLogin() + '%'));
        }
        if (userInfoDTO.getEmail() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("email"), '%' + userInfoDTO.getEmail() + '%'));
        }
        if (userInfoDTO.getRole() != null) {
            if (EnumUtils.isValidEnum(Role.class, userInfoDTO.getRole())) {
                predicates.add(criteriaBuilder.equal(userRoot.get("role"), Role.valueOf(userInfoDTO.getRole().toUpperCase())));
            } else {
                throw new MyValidationException(ExceptionCode.WRONG_ROLE);
            }
        }
        if (userInfoDTO.getName() != null) {
            predicates.add(criteriaBuilder.like(join.get("name"), '%' + userInfoDTO.getName() + '%'));
        }
        if (userInfoDTO.getSurname() != null) {
            predicates.add(criteriaBuilder.like(join.get("surname"), '%' + userInfoDTO.getSurname() + '%'));
        }
        if (userInfoDTO.getAddress() != null) {
            predicates.add(criteriaBuilder.like(join.get("address"), '%' + userInfoDTO.getAddress() + '%'));
        }
        if (userInfoDTO.getPhoneNumber() != null) {
            predicates.add(criteriaBuilder.like(join.get("phoneNumber"), '%' + userInfoDTO.getPhoneNumber() + '%'));
        }
        criteriaQuery.select(userRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("id"), id),
                criteriaBuilder.equal(userRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }

    @Override
    public Optional<User> getUserInfo(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Join<User, UserDetails> info = criteriaQuery.from(User.class).join("userDetails");
        criteriaQuery.where(criteriaBuilder.equal(info.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }

    @Override
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        user.setStatus(Status.DELETED);
        return user;
    }

    @Override
    public User updateUser(long id, UserUpdateDTO userUpdateDTO) {
        User user = entityManager.find(User.class, id);
        UserDetails userDetails = entityManager.find(UserDetails.class, id);
        if (userUpdateDTO.getPassword() != null) {
            if (userUpdateDTO.getOldPassword().equals(user.getPassword())) {
                user.setPassword(userUpdateDTO.getPassword());
            } else {
                throw new UpdateException(ExceptionCode.WRONG_PASSWORD);
            }
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getName() != null) {
            userDetails.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getSurname() != null) {
            userDetails.setSurname(userUpdateDTO.getSurname());
        }
        if (userUpdateDTO.getAddress() != null) {
            userDetails.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getPhoneNumber() != null) {
            userDetails.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }
        if (user.getRole().equals(Role.USER) && user.getUserDetails().getName() != null
                && user.getUserDetails().getSurname() != null && user.getUserDetails().getAddress() != null
                && user.getUserDetails().getPhoneNumber() != null) {
            user.setRole(Role.CUSTOMER);
        }
        return user;
    }

    @Override
    public User updateUserRole(long id, String role) {
        User user = entityManager.find(User.class, id);
        user.setRole(Role.valueOf(role));
        return user;
    }

    @Override
    public List<Pet> getUserPets(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> petRoot = criteriaQuery.from(Pet.class);
        Join<Pet, User> pets = petRoot.join("owners");
        criteriaQuery.where(criteriaBuilder.equal(pets.get("id"), id),
                criteriaBuilder.equal(petRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean deleteUserPets(long id) {
        User user = entityManager.find(User.class, id);
        List<Pet> pets = getUserPets(id);
        for (Pet pet : pets) {
            if (pet.getOwners().size() > 1) {
                entityManager.find(Pet.class, pet.getId()).getOwners().remove(user);
            } else {
                entityManager.find(Pet.class, pet.getId()).setStatus(Status.DELETED);
            }
        }
        return true;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> criteriaQuery = criteriaBuilder.createQuery(Appointment.class);
        Join<Appointment, User> join = criteriaQuery.from(Appointment.class).join("user");
        criteriaQuery.where(criteriaBuilder.equal(join.get("id"), userId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
