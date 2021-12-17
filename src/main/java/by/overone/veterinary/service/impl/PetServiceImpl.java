package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.dao.impl.PetDAOImpl;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.service.exception.ServiceException;
import by.overone.veterinary.service.exception.ServiceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class PetServiceImpl implements PetService {

    private final PetDAO petDAO = new PetDAOImpl();

    @Override
    public List<PetDataDTO> getPets() throws ServiceException {
        List<PetDataDTO> petsDataDTO;

        try {
            petsDataDTO = petDAO.getPets().stream()
                    .map(pet -> new PetDataDTO(pet.getId(), pet.getName(), pet.getType(), pet.getBreed(), pet.getAge()))
                    .collect(Collectors.toList());
        }catch (DaoException e) {
            throw new ServiceException(e);
        }
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) throws ServiceNotFoundException, ServiceException {
        PetDataDTO petDataDTO = new PetDataDTO();
        try {
            Pet pet = petDAO.getPetById(id);
            petDataDTO.setId(pet.getId());
            petDataDTO.setName(pet.getName());
            petDataDTO.setType(pet.getType());
            petDataDTO.setBreed(pet.getBreed());
            petDataDTO.setAge(pet.getAge());
        } catch (DaoNotFoundException e) {
            throw new ServiceNotFoundException("Pet not found", e);
        }catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return petDataDTO;
    }

    @Override
    public void addPet(long pet_id, Pet pet) {

    }

    @Override
    public void updatePet(long id, Pet pet) throws ServiceException, ServiceNotFoundException {
        getPetById(id);
        try {
            petDAO.updatePet(id, pet);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deletePet(long id) {

    }
}
