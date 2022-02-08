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
import by.overone.veterinary.util.mapper.MyMapper;
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
    private final MyMapper myMapper;

    @Override
    public List<PetDataDTO> getPets() {
        List<PetDataDTO> petsDataDTO;
        petsDataDTO = petDAO.getPets().stream()
                .map(pet -> myMapper.petToDTO(pet))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public List<PetDataDTO> getPetsByParams(PetDataDTO petDataDTO) {
        List<PetDataDTO> petsDataDTO;
        petsDataDTO = petDAO.getPetsByParams(petDataDTO).stream()
                .map(pet ->  myMapper.petToDTO(pet))
                .collect(Collectors.toList());
        return petsDataDTO;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        Pet pet = petDAO.getPetById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_PET));
        return  myMapper.petToDTO(pet);
    }

    @Override
    public PetDataDTO addPet(PetDataDTO petDataDTO) {
        Pet pet = myMapper.dtoToPet(petDataDTO);
        pet.setStatus(Status.ACTIVE);
        return myMapper.petToDTO(petDAO.addPet(pet));
    }

    @Override
    public PetDataDTO deletePet(long id) {
        getPetById(id);
        return myMapper.petToDTO(petDAO.deletePet(id));
    }


    @Override
    public PetDataDTO updatePet(long id, PetDataDTO petDataDTO){
        getPetById(id);
        petDAO.updatePet(id, myMapper.dtoToPet(petDataDTO));
        return getPetById(id);
    }

    @Override
    public List<UserDataDTO> getPetOwners(long id) {
        getPetById(id);
        petDAO.getPetById(id);
        return petDAO.getPetOwners(id).stream()
                .filter(user -> user.getStatus().equals(Status.ACTIVE))
                .map(user -> myMapper.userToDataDTO(user))
                .collect(Collectors.toList());
    }
}
