package by.overone.veterinary.service.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.service.exception.EntityAlreadyExistException;
import by.overone.veterinary.service.exception.EntityNotFoundException;
import by.overone.veterinary.service.exception.ExceptionCode;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.util.mapper.MyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetDAO petDAO;
    private final MyMapper myMapper;

    @Override
    public List<PetDataDTO> getPetsByParams(PetDataDTO petDataDTO) {
        List<PetDataDTO> pets;
        pets = petDAO.getPetsByParams(petDataDTO).stream()
                .map(myMapper::petToDTO)
                .collect(Collectors.toList());

        if (petDataDTO.getOwners() != null) {
            pets = pets.stream()
                    .filter(pet ->pet.getOwners().containsAll(petDataDTO.getOwners()))
                    .collect(Collectors.toList());
        }
        return pets;
    }

    @Override
    public PetDataDTO getPetById(long id) {
        Pet pet = petDAO.getPetById(id)
                .orElseThrow(()->new EntityNotFoundException(ExceptionCode.NOT_EXISTING_PET));
        return  myMapper.petToDTO(pet);
    }

    @Override
    public PetDataDTO addPet(PetDataDTO petDataDTO) {
        try {
        Pet pet = myMapper.dtoToPet(petDataDTO);
        pet.setStatus(Status.ACTIVE);
        return myMapper.petToDTO(petDAO.addPet(pet));
        }catch (PersistenceException e){
            throw new EntityAlreadyExistException(ExceptionCode.ALREADY_EXISTING_PET);
        }
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
                .map(myMapper::userToDataDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDataDTO> getAppointmentsByPetId(long petId) {
        List<AppointmentDataDTO> appointmentsDataDTO;
        appointmentsDataDTO = petDAO.getAppointmentsByPetId(petId).stream()
                .map(myMapper::appointmentToDTO)
                .collect(Collectors.toList());

        return appointmentsDataDTO;
    }
}
