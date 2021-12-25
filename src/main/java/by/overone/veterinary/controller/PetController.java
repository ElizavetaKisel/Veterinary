package by.overone.veterinary.controller;

import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pets")
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

    @GetMapping("/delete/{id}")
    public void deletePet(@PathVariable long id) {
        petService.deletePet(id);
    }
}
