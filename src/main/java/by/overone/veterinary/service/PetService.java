package by.overone.veterinary.service;

import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.service.exception.ServiceException;

import java.util.List;

public interface PetService {

    List<PetDataDTO> getPets() throws ServiceException;

    PetDataDTO getPetById(long id);

    void addPet(long user_id, Pet pet);

    void updatePet(long id, Pet pet) throws ServiceException;

    void deletePet(long id);

}
