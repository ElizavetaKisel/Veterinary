package by.overone.veterinary.controller;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.UserDetails;
import by.overone.veterinary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
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

    @GetMapping("/{id}/my-pets")
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
    public void addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.addUser(userRegistrationDTO);
    }

    @PutMapping
    public UserDataDTO updateUser(@RequestBody UserUpdateDTO user) {
        return userService.updateUser(user);
    }

    @PutMapping("/update-details")
    public UserDetails updateUserDetails(@RequestBody UserDetails user) {
        return userService.updateUserDetails(user);
    }
}
