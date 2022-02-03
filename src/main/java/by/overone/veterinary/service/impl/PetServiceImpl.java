package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.exception.EntityAlreadyExistException;
import by.overone.veterinary.exception.EntityNotFoundException;
import by.overone.veterinary.exception.ExceptionCode;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.model.User;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetDAO petDAO;
    private final UserDAO userDAO;

    @Override
    public List<PetDataDTO> getPets() {
        List<PetDataDTO> petsDataDTO;

        petsDataDTO = petDAO.getPets().stream()
                .map(pet -> new PetDataDTO(pet.getName(), pet.getType(), pet.getBreed(), pet.getAge(), pet.getOwners().stream().map(o -> o.getId()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        PetDataDTO petDataDTO = new PetDataDTO();
        Pet pet = petDAO.getPetById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_PET));
        petDataDTO.setName(pet.getName());
        petDataDTO.setType(pet.getType());
        petDataDTO.setBreed(pet.getBreed());
        petDataDTO.setAge(pet.getAge());
        petDataDTO.setOwners(pet.getOwners().stream().map(o -> o.getId()).collect(Collectors.toList()));
        return petDataDTO;
    }

    @Override
    public void addPet(PetDataDTO petDataDTO) {
            Pet pet = new Pet();
            pet.setName(petDataDTO.getName());
            pet.setType(petDataDTO.getType());
            pet.setBreed(petDataDTO.getBreed());
            pet.setStatus(Status.ACTIVE);
            pet.setAge(petDataDTO.getAge());
            pet.setOwners(petDataDTO.getOwners().stream().map(o -> userDAO.getUserById(o.longValue())
                    .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER))).collect(Collectors.toList()));
            petDAO.addPet(pet);
    }

    @Override
    public void deletePet(long id) {
        getPetById(id);
        deletePet(id);
    }

    @Override
    public PetDataDTO updatePet(long id, PetDataDTO petDataDTO){
        getPetById(id);
        Pet pet = new Pet();
        petDAO.updatePet(id, pet);
        pet.setName(petDataDTO.getName());
        pet.setType(petDataDTO.getType());
        pet.setBreed(petDataDTO.getBreed());
        pet.setAge(petDataDTO.getAge());
        pet.setOwners(petDataDTO.getOwners().stream().map(o -> userDAO.getUserById(o.longValue())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.NOT_EXISTING_USER))).collect(Collectors.toList()));
        return getPetById(id);
    }

    @Override
    public List<UserDataDTO> getPetOwners(long id) {
        getPetById(id);
        petDAO.getPetById(id);
        return petDAO.getPetOwners(id).stream()
                .filter(user -> user.getStatus().equals(Status.ACTIVE))
                .map(user -> new UserDataDTO(user.getLogin(), user.getEmail(), user.getRole().toString()))
                .collect(Collectors.toList());
    }
}
