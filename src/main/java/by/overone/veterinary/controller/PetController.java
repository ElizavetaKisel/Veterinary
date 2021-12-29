package by.overone.veterinary.controller;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.service.PetService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    @GetMapping
    public List<PetDataDTO> readAll() {
        return petService.getPets();
    }

    @GetMapping("/{id}")
    public PetDataDTO petById(@PathVariable long id) {
        return petService.getPetById(id);
    }

    @GetMapping("/user/{id}")
    public List<PetDataDTO> petsByUserId(@PathVariable long id) {
        return petService.getPetsByUserId(id);
    }

    @PostMapping("/add/{user_id}")
    public void addPet(@PathVariable long user_id, @RequestBody PetDataDTO petDataDTO) throws ValidationException {
        petService.addPet(user_id, petDataDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }

    @PostMapping("{id}/update")
    public PetDataDTO updateUser(@PathVariable long id, @RequestBody PetDataDTO pet) throws ValidationException {
        return petService.updatePet(id, pet);
    }
}
