package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.exception.EntityAlreadyExistException;
import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.exception.ExceptionCode;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PetDAOImpl implements PetDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pet> getPets() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> petRoot = criteriaQuery.from(Pet.class);
        criteriaQuery.where(criteriaBuilder.equal(petRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<Pet> getPetById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> petRoot = criteriaQuery.from(Pet.class);
        criteriaQuery.where(criteriaBuilder.equal(petRoot.get("id"), id), criteriaBuilder.equal(petRoot.get("status"), Status.ACTIVE));
        return entityManager.createQuery(criteriaQuery).getResultList().stream().findAny();
    }

    @Override
    public Pet addPet(Pet pet) {
        try {
            entityManager.persist(pet);
            return pet;
        }catch (PersistenceException e){
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_USER);
        }
    }

    @Override
    public Pet deletePet(long id) {
        Pet pet = entityManager.find(Pet.class, id);
        pet.setStatus(Status.DELETED);
        return pet;
    }

    @Override
    public List<User> getPetOwners(long id) {
        Pet pet = entityManager.find(Pet.class, id);
        return pet.getOwners();
    }

    @Override
    public Pet updatePet(long id, Pet pet) {
        Pet petDB = entityManager.find(Pet.class, id);
        if (pet.getName() != null){
            petDB.setName(pet.getName());
        }
        if (pet.getType() != null){
            petDB.setType(pet.getType());
        }
        if (pet.getBreed() != null){
            petDB.setBreed(pet.getBreed());
        }
        if (pet.getAge() != null){
            petDB.setAge(pet.getAge());
        }
        if (pet.getOwners() != null){
            petDB.setOwners(pet.getOwners());
        }
        return petDB;
    }

}
