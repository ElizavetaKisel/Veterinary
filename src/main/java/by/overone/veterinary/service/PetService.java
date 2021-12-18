package by.overone.veterinary.service;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceExistException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;
import by.overone.veterinary.util.validator.exception.ValidationException;

import java.util.List;

public interface PetService {

    List<PetDataDTO> getPets() throws ServiceException;

    PetDataDTO getPetById(long id) throws ServiceNotFoundException, ServiceException;

    void addPet(long user_id, PetDataDTO petDataDTO) throws ServiceExistException, ServiceException, ValidationException;

    void updatePet(long id, Pet pet) throws ServiceException, ServiceNotFoundException;

    void deletePet(long id) throws ServiceException, ServiceNotFoundException;

    List<PetDataDTO> getPetsByUserId(long user_id) throws ServiceNotFoundException, ServiceException;

}
