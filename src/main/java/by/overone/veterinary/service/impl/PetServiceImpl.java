package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.exception.ExceptionCode;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.User;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.service.UserService;
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
                .map(pet -> new PetDataDTO(pet.getName(), pet.getType(), pet.getBreed(), pet.getAge(), pet.getOwners()))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        PetDataDTO petDataDTO = new PetDataDTO();
        Pet pet = petDAO.getPetById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER));
        petDataDTO.setName(pet.getName());
        petDataDTO.setType(pet.getType());
        petDataDTO.setBreed(pet.getBreed());
        petDataDTO.setAge(pet.getAge());
        return petDataDTO;
    }

    @Override
    public void addPet(PetDataDTO petDataDTO) {
        Pet pet = new Pet();
        pet.setName(petDataDTO.getName());
        pet.setType(petDataDTO.getType());
        pet.setBreed(petDataDTO.getBreed());
        pet.setAge(petDataDTO.getAge());
        pet.setOwners(petDataDTO.getOwners());
        petDAO.addPet(pet);
    }

    @Override
    public void deletePet(long id) {
        getPetById(id);
        deletePet(id);
    }

    @Override
    public PetDataDTO updatePet(long id, PetDataDTO pet){
        getPetById(id);
        PetDataDTO petDataDTO = new PetDataDTO();
        petDAO.updatePet(id, pet);
        petDataDTO.setName(pet.getName());
        petDataDTO.setType(pet.getType());
        petDataDTO.setBreed(pet.getBreed());
        petDataDTO.setAge(pet.getAge());
        return petDataDTO;
    }

    @Override
    public List<UserDataDTO> getPetOwners(long id) {
        petDAO.getPetById(id);
        return petDAO.getPetOwners(id).stream()
                .map(user -> new UserDataDTO(user.getLogin(), user.getEmail(), user.getRole().toString()))
                .collect(Collectors.toList());
    }
}
