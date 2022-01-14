package by.overone.veterinary.controller;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDataDTO> readAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDataDTO userById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/my_pets")
    public List<PetDataDTO> petById(@PathVariable long id) {
        return userService.getPetsByUserId(id);
    }

    @GetMapping("{id}/info")
    public UserInfoDTO userInfo(@PathVariable long id) {
        return userService.getUserInfo(id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping
    public void addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ValidationException {
        userService.addUser(userRegistrationDTO);
    }

    @PutMapping
    public UserDataDTO updateUser(@RequestBody UserUpdateDTO user) throws ValidationException {
        return userService.updateUser(user);
    }

    @PutMapping("/update_details")
    public UserDetails updateUserDetails(@RequestBody UserDetails user) throws ValidationException {
        return userService.updateUserDetails(user);
    }

    @GetMapping("/user/{id}")
    public List<PetDataDTO> petsByUserId(@PathVariable long id) {
        return userService.getPetsByUserId(id);
    }
}
