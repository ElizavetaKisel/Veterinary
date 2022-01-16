package by.overone.veterinary.controller;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.dto.UserRegistrationDTO;
import by.overone.veterinary.service.UserService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UIUserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDataDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping
    public String addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws ValidationException {
        userService.addUser(userRegistrationDTO);
        return "users";
    }
}