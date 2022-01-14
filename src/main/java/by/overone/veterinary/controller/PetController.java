package by.overone.veterinary.controller;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/pets")
public class PetController {
    private final PetService petService;

    @GetMapping
    public List<PetDataDTO> readAll() {
        return petService.getPets();
    }

    @GetMapping("/{id}/owners")
    public List<UserDataDTO> readOwners(@PathVariable long id) {
        return petService.getUsersByPetId(id);
    }

    @GetMapping("/{id}")
    public PetDataDTO petById(@PathVariable long id) {
        return petService.getPetById(id);
    }

    @PostMapping
    public void addPet(@RequestBody PetDataDTO petDataDTO) throws ValidationException {
        petService.addPet(1, petDataDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }

    @PutMapping
    public PetDataDTO updatePet(@RequestBody PetDataDTO pet) throws ValidationException {
        return petService.updatePet(pet);
    }
}
