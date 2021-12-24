package by.overone.veterinary.controller;

import by.overone.veterinary.dto.UserDataDTO;
import by.overone.veterinary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/hello")
    public String read() {
        return "hello";
    }
}
