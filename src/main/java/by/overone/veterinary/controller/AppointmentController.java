package by.overone.veterinary.controller;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.model.Appointment;
import by.overone.veterinary.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

//    @GetMapping
//    public List<AppointmentDataDTO> readAll() {
//        return appointmentService.getAppointments();
//    }

    @GetMapping
    public List<AppointmentDataDTO> readAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        return appointmentService.getAppointmentsByParams(appointmentDataDTO);
    }

    @GetMapping("/appointments/doctor/{id}")
    public List<AppointmentDataDTO> appointmentsByDoctorId(@PathVariable long id) {
        return appointmentService.getAppointmentsByDoctorId(id);
    }

    @GetMapping("/{id}")
    public AppointmentDataDTO appointmentById(@PathVariable long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public AppointmentDataDTO addAppointment(@RequestBody AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.addAppointment(appointmentNewDTO);
    }

    @PatchMapping("/{id}")
    public AppointmentDataDTO updateAppointment(@PathVariable long id, @RequestBody AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.updateAppointment(id, appointmentNewDTO);
    }

    @DeleteMapping("/{id}")
    public AppointmentDataDTO deleteAppointment(@PathVariable long id) {
        return appointmentService.deleteAppointment(id);
    }

    @PutMapping("/{id}")
    public AppointmentDataDTO closeAppointment(@PathVariable long id, @RequestParam String diagnosis) {
        return appointmentService.closeAppointment(id, diagnosis);
    }
}
