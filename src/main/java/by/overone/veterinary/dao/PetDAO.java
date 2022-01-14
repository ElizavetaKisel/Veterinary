package by.overone.veterinary.dao;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.model.Pet;

import java.util.List;

public interface PetDAO {

    List<Pet> getPets();

    Pet getPetById(long id);

    Pet addPet(long user_id, Pet pet);

    boolean deletePet(long id);

    List<UserDataDTO> getUsersByPetId(long pet_id);

    PetDataDTO updatePet(PetDataDTO pet);
}
