package by.overone.veterinary.controller;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.validator.NewEntity;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/pets")
public class PetController {
    private final PetService petService;
    private final AppointmentService appointmentService;

//    @GetMapping
//    public List<PetDataDTO> readAllPets() {
//        return petService.getPets();
//    }

    @GetMapping
    public List<PetDataDTO> readPetsByParams(PetDataDTO petDataDTO) {
        System.out.println(petDataDTO.getOwners());
        return petService.getPetsByParams(petDataDTO);
    }

    @GetMapping("/{id}/appointments")
    public List<AppointmentDataDTO> appointmentsByUserId(@PathVariable long id) {
        return appointmentService.getAppointmentsByPetId(id);
    }

    @GetMapping("/{id}/owners")
    public List<UserDataDTO> readOwners(@PathVariable long id) {
        return petService.getPetOwners(id);
    }

    @GetMapping("/{id}")
    public PetDataDTO petById(@PathVariable long id) {
        return petService.getPetById(id);
    }

    @PostMapping
    public void addPet(@Validated(NewEntity.class) @RequestBody PetDataDTO petDataDTO) {
        petService.addPet(petDataDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }

    @PatchMapping("{id}")
    public PetDataDTO updatePet(@PathVariable long id, @Validated @RequestBody PetDataDTO pet) {
        return petService.updatePet(id, pet);
    }
}
