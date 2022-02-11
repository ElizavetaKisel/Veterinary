package by.overone.veterinary.controller;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.validator.NewEntity;
import by.overone.veterinary.validator.UpdateEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/pets")
@Validated
public class PetController {
    private final PetService petService;

    @GetMapping
    public List<PetDataDTO> readPetsByParams(PetDataDTO petDataDTO) {
        return petService.getPetsByParams(petDataDTO);
    }

    @GetMapping("/{id}/owners")
    public List<UserDataDTO> readOwners(@PathVariable @Valid @Min(1) long id) {
        return petService.getPetOwners(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PetDataDTO petById(@PathVariable @Valid @Min(1) long id) {
        return petService.getPetById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDataDTO addPet(@Validated(NewEntity.class) @RequestBody PetDataDTO petDataDTO) {
        return petService.addPet(petDataDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable @Valid @Min(1) long id) {
        petService.deletePet(id);
    }

    @PatchMapping("{id}")
    public PetDataDTO updatePet(@PathVariable @Valid @Min(1) long id, @Validated(UpdateEntity.class) @RequestBody PetDataDTO pet) {
        return petService.updatePet(id, pet);
    }
}
