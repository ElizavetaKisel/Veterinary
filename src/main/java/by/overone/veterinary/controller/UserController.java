package by.overone.veterinary.controller;

import by.overone.veterinary.dto.*;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.service.UserService;
import lombok.AllArgsConstructor;
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
    private final AppointmentService appointmentService;

    @GetMapping
    public List<UserInfoDTO> readUsersByParams(UserInfoDTO userInfoDTO) {
        return userService.getUsersByParams(userInfoDTO);
    }

    @GetMapping("/{id}")
    public UserDataDTO userById(@PathVariable @Valid @Min(1) long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/pets")
    public List<PetDataDTO> petByUserId(@PathVariable long id) {
        return userService.getUserPets(id);
    }

    @GetMapping("/{id}/appointments")
    public List<AppointmentDataDTO> appointmentsByUserId(@PathVariable long id) {
        return userService.getAppointmentsByUserId(id);
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
    public UserDataDTO updateUserRole(@PathVariable long id, @RequestBody String role) {
        return userService.updateUserRole(id, role);
    }

    @PutMapping("/{userId}/appointments")
    public AppointmentDataDTO makeAppointment(@PathVariable long userId, @RequestParam long appointmentId,
                                @RequestParam long petId, @RequestParam String reason) {
        return appointmentService.makeAppointment(userId, appointmentId, petId, reason);
    }
    @PutMapping("/appointments/{appointmentId}")
    public AppointmentDataDTO returnAppointment(@PathVariable long appointmentId) {
        return appointmentService.returnAppointment(appointmentId);
    }

}
