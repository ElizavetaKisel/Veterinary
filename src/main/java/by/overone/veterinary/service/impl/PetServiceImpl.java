package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.validator.PetValidator;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetDAO petDAO;
    private final UserService userService;

    @Override
    public List<PetDataDTO> getPets() {
        List<PetDataDTO> petsDataDTO;

        petsDataDTO = petDAO.getPets().stream()
                .map(pet -> new PetDataDTO(pet.getId(), pet.getName(), pet.getType(), pet.getBreed(), pet.getAge()))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        PetDataDTO petDataDTO = new PetDataDTO();
        Pet pet = petDAO.getPetById(id);
        petDataDTO.setId(pet.getId());
        petDataDTO.setName(pet.getName());
        petDataDTO.setType(pet.getType());
        petDataDTO.setBreed(pet.getBreed());
        petDataDTO.setAge(pet.getAge());
        return petDataDTO;
    }

    @Override
    public void addPet(long user_id, PetDataDTO petDataDTO) throws ValidationException {
        PetValidator.validatePet(petDataDTO);
        Pet pet = new Pet();
        pet.setName(petDataDTO.getName());
        pet.setType(petDataDTO.getType());
        pet.setBreed(petDataDTO.getBreed());
        pet.setAge(petDataDTO.getAge());
        petDAO.addPet(user_id, pet);
    }

    @Override
    public void deletePet(long id) {
        getPetById(id);
    }

    @Override
    public List<PetDataDTO> getPetsByUserId(long user_id) {
        userService.getUserById(user_id);
        List<PetDataDTO> petsDataDTO;
        petsDataDTO = petDAO.getPetsByUserId(user_id).stream()
                .map(pet -> new PetDataDTO(pet.getId(), pet.getName(), pet.getType(), pet.getBreed(), pet.getAge()))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

//    @Override
//    public void updatePet(long id, Pet pet) throws ServiceException, ServiceNotFoundException {
//        getPetById(id);
//        petDAO.updatePet(id, pet);
//    }
}
