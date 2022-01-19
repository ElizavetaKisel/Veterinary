package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.model.Pet;
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
                .map(pet -> new PetDataDTO(pet.getPet_id(), petDAO.getUsersByPetId(pet.getPet_id()).stream().map(user -> user.getUser_id()).collect(Collectors.toList()),
                        pet.getName(), pet.getType(), pet.getBreed(), pet.getAge()))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        PetDataDTO petDataDTO = new PetDataDTO();
        Pet pet = petDAO.getPetById(id);
        petDataDTO.setPet_id(pet.getPet_id());
        petDataDTO.setName(pet.getName());
        petDataDTO.setType(pet.getType());
        petDataDTO.setBreed(pet.getBreed());
        petDataDTO.setAge(pet.getAge());
        return petDataDTO;
    }

    @Override
    public void addPet(long user_id, PetDataDTO petDataDTO) {
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
        deletePet(id);
    }

    @Override
    public PetDataDTO updatePet(PetDataDTO pet){
        getPetById(pet.getPet_id());
        return petDAO.updatePet(pet);
    }

    @Override
    public List<UserDataDTO> getUsersByPetId(long pet_id) {
        petDAO.getPetById(pet_id);
        return petDAO.getUsersByPetId(pet_id);
    }
}
