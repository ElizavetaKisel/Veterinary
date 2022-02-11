package by.overone.veterinary.controller;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.validator.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserDataDTO> readUsers() {
        return userService.getAllUsers();
    }

    @GetMapping
    public List<UserInfoDTO> readUsersByParams(@Validated UserInfoDTO userInfoDTO) {
        return userService.getUsersByParams(userInfoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserDataDTO userById(@PathVariable @Valid @Min(1) long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/pets")
    public List<PetDataDTO> petByUserId(@PathVariable @Valid @Min(1) long id) {
        return userService.getUserPets(id);
    }

    @GetMapping("{id}/info")
    public UserInfoDTO userInfo(@PathVariable @Valid @Min(1) long id) {
        return userService.getUserInfo(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Valid @Min(1) long id) {
        userService.deleteUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@Validated @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.addUser(userRegistrationDTO);
    }

    @PatchMapping("/{id}")
    public UserInfoDTO updateUser(@PathVariable @Valid @Min(1) long id, @Validated @RequestBody UserUpdateDTO user) {
        return userService.updateUser(id, user);
    }

    @PatchMapping("/{id}/role")
    public UserDataDTO updateUserRole(@PathVariable @Valid @Min(1) long id, @Valid @RequestParam @Role String role) {
        return userService.updateUserRole(id, role);
    }

}
