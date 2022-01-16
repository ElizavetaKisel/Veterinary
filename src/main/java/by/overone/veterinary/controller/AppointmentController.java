package by.overone.veterinary.controller;

import by.overone.veterinary.dto.AppointmentActiveDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.service.AppointmentService;
import by.overone.veterinary.util.validator.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> readAll() {
        return appointmentService.getAppointments();
    }

    @GetMapping("/{id}")
    public Appointment appointmentById(@PathVariable long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/user/{id}")
    public List<Appointment> appointmentsByUserId(@PathVariable long id) {
        return appointmentService.getAppointmentsByUserId(id);
    }
    @GetMapping("/doctor/{id}")
    public List<Appointment> appointmentsByDoctorId(@PathVariable long id) {
        return appointmentService.getAppointmentsByDoctorId(id);
    }
    @GetMapping("/pet/{id}")
    public List<Appointment> appointmentsByPetId(@PathVariable long id) {
        return appointmentService.getAppointmentsByPetId(id);
    }

    @PostMapping
    public void addAppointment(@RequestBody AppointmentActiveDTO appointmentActiveDTO) throws ValidationException {
        appointmentService.addAppointment(appointmentActiveDTO);
    }

    @PutMapping
    public Appointment updateUser(@RequestBody Appointment appointment) throws ValidationException {
        return appointmentService.updateAppointment(appointment);
    }
}
