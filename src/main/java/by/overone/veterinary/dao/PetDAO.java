package by.overone.veterinary.dao;


import by.overone.veterinary.model.Pet;

import java.util.List;

public interface PetDAO {

    List<Pet> getPets();

    Pet getPetById(long id);

    Pet addPet(Pet pet);

  //  Pet updatePet(Pet pet);

    boolean deletePet(long id);
}
