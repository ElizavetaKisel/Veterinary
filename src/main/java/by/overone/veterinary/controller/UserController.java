package by.overone.veterinary.controller;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.User;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
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

    @GetMapping("/info/{id}")
    public UserInfoDTO userInfo(@PathVariable long id) {
        return userService.getUserInfo(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ValidationException {
        userService.addUser(userRegistrationDTO);
    }

    @PostMapping("/update/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserUpdateDTO user) throws ValidationException {
        userService.updateUser(id, user);
    }
}
