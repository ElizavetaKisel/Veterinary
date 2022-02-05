package by.overone.veterinary.service;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;

import java.util.List;

public interface PetService {

    List<PetDataDTO> getPets();

    List<PetDataDTO> getPetsByParams(PetDataDTO petDataDTO);

    PetDataDTO getPetById(long id);

    void addPet(PetDataDTO petDataDTO);

    void deletePet(long id);

    PetDataDTO updatePet(long id, PetDataDTO pet);

    List<UserDataDTO> getPetOwners(long id);

}