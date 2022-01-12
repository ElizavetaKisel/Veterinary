package by.overone.veterinary.controller;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserInfoDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.dto.UserUpdateDTO;
import by.overone.veterinary.model.User;
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

    @GetMapping("{id}/info")
    public UserInfoDTO userInfo(@PathVariable long id) {
        return userService.getUserInfo(id);
    }

    @DeleteMapping("{id}/delete")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ValidationException {
        userService.addUser(userRegistrationDTO);
    }

    @PostMapping("/update")
    public UserDataDTO updateUser(@RequestBody UserUpdateDTO user) throws ValidationException {
        return userService.updateUser(user);
    }

    @PostMapping("/update_details")
    public UserDetails updateUserDetails(@RequestBody UserDetails user) throws ValidationException {
        return userService.updateUserDetails(user);
    }
}
