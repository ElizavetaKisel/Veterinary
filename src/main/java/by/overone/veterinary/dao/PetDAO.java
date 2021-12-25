package by.overone.veterinary.dao;

import by.overone.veterinary.model.Pet;

import java.util.List;

public interface PetDAO {

    List<Pet> getPets();

    Pet getPetById(long id);

    Pet addPet(long user_id, Pet pet);

    boolean deletePet(long id);

    List<Pet> getPetsByUserId(long user_id);

//    Pet updatePet(long id, Pet pet);
}
