package by.overone.veterinary.controller;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.model.User;
import by.overone.veterinary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/params")
    public List<UserInfoDTO> readUsersByParams(UserInfoDTO userInfoDTO) {
        return userService.getUsersByParams(userInfoDTO);
    }

    @GetMapping("/{id}")
    public UserDataDTO userById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/pets")
    public List<PetDataDTO> petByUserId(@PathVariable long id) {
        return userService.getUserPets(id);
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
    public void addUser(@Validated @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.addUser(userRegistrationDTO);
    }

    @PatchMapping("/{id}")
    public UserInfoDTO updateUser(@PathVariable long id, @Validated @RequestBody UserUpdateDTO user) {
        return userService.updateUser(id, user);
    }

    @PatchMapping("/{id}/role")
    public User updateUserRole(@PathVariable long id, @RequestBody String role) {
        return userService.updateUserRole(id, role);
    }
}
