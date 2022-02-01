package by.overone.veterinary.dao.impl;


import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<User> getUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("id"), id), criteriaBuilder.equal(userRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }

    @Override
    public Optional<User> getUserInfo(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Join<User, UserDetails> info = criteriaQuery.from(User.class).join("userDetails");
        criteriaQuery.where(criteriaBuilder.equal(info.get("id"), id), criteriaBuilder.equal(info.get("status"), Status.ACTIVE));
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
        if (userUpdateDTO.getPassword() != null){
            user.setPassword(userUpdateDTO.getPassword());
        }
        if (userUpdateDTO.getEmail() != null){
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getName() != null){
            userDetails.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getSurname() != null){
            userDetails.setSurname(userUpdateDTO.getSurname());
        }
        if (userUpdateDTO.getAddress() != null){
            userDetails.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getPhoneNumber() != null){
            userDetails.setPhoneNumber(userUpdateDTO.getPhoneNumber());
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
        criteriaQuery.where(criteriaBuilder.equal(pets.get("id"), id), criteriaBuilder.equal(petRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean deleteUserPets(long id) {
        User user = entityManager.find(User.class, id);
        List<Pet> pets = getUserPets(id);
        for (Iterator<Pet> i = pets.iterator(); i.hasNext(); ) {
            Pet pet = i.next();
            if (pet.getOwners().size() > 1) {
                pet.getOwners().remove(user);
            } else {
                pet.setStatus(Status.DELETED);
            }
        }
        return true;
    }
    
}
