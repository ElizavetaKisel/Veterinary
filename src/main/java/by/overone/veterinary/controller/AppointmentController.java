package by.overone.veterinary.controller;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentMakeDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDataDTO> readAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        return appointmentService.getAppointmentsByParams(appointmentDataDTO);
    }

    @GetMapping("/appointments/doctor/{id}")
    public List<AppointmentDataDTO> appointmentsByDoctorId(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.getAppointmentsByDoctorId(id);
    }

    @GetMapping("/{id}")
    public AppointmentDataDTO appointmentById(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public AppointmentDataDTO addAppointment(@RequestBody @Valid @Min(1) AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.addAppointment(appointmentNewDTO);
    }

    @PatchMapping("/{id}")
    public AppointmentDataDTO updateAppointment(@PathVariable @Valid @Min(1) long id,
                                                @RequestBody AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.updateAppointment(id, appointmentNewDTO);
    }

    @DeleteMapping("/{id}")
    public AppointmentDataDTO deleteAppointment(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.deleteAppointment(id);
    }

    @PutMapping("/{id}")
    public AppointmentDataDTO closeAppointment(@PathVariable @Valid @Min(1) long id,
                                               @RequestParam @Valid @NotBlank @NotNull @Min(5) String diagnosis) {
        return appointmentService.closeAppointment(id, diagnosis);
    }

    @PatchMapping("/{id}/make")
    public AppointmentDataDTO makeAppointment(@PathVariable @Valid @Min(1) long id,
                                              @RequestBody AppointmentMakeDTO appointmentMakeDTO) {
        return appointmentService.makeAppointment(id, appointmentMakeDTO);
    }
    @PutMapping("/{id}/return")
    public AppointmentDataDTO returnAppointment(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.returnAppointment(id);
    }
}
