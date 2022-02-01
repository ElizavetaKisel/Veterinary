package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

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
        entityManager.persist(pet);
        return pet;
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
    public Pet updatePet(long id, PetDataDTO petDataDTO) {
        Pet pet = entityManager.find(Pet.class, id);
        if (petDataDTO.getName() != null){
            pet.setName(petDataDTO.getName());
        }
        if (petDataDTO.getType() != null){
            pet.setType(petDataDTO.getType());
        }
        if (petDataDTO.getBreed() != null){
            pet.setBreed(petDataDTO.getBreed());
        }
        if (petDataDTO.getAge() != null){
            pet.setAge(petDataDTO.getAge());
        }
        return pet;
    }

}
