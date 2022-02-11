package by.overone.veterinary.dao;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.User;

import java.util.List;
import java.util.Optional;

public interface PetDAO {

    List<Pet> getPetsByParams(PetDataDTO petDataDTO);

    Optional<Pet> getPetById(long id);

    Pet addPet(Pet pet);

    Pet deletePet(long id);

    List<User> getPetOwners(long id);

    Pet updatePet(long id, PetDataDTO petDataDTO);
}
