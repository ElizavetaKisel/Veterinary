package by.overone.veterinary.service;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.util.validator.exception.ValidationException;

import java.util.List;

public interface PetService {

    List<PetDataDTO> getPets();

    PetDataDTO getPetById(long id);

    void addPet(long user_id, PetDataDTO petDataDTO) throws ValidationException;

    void deletePet(long id);

    PetDataDTO updatePet(PetDataDTO pet) throws ValidationException;

    List<UserDataDTO> getUsersByPetId(long pet_id);

}