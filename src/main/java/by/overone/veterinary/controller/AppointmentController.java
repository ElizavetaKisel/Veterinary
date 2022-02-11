package by.overone.veterinary.controller;

import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.dto.AppointmentMakeDTO;
import by.overone.veterinary.dto.AppointmentNewDTO;
import by.overone.veterinary.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/appointments")
@Validated
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDataDTO> readAppointmentsByParams(AppointmentDataDTO appointmentDataDTO) {
        return appointmentService.getAppointmentsByParams(appointmentDataDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public AppointmentDataDTO appointmentById(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDataDTO addAppointment(@RequestBody @Validated AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.addAppointment(appointmentNewDTO);
    }

    @PatchMapping("/{id}")
    public AppointmentDataDTO updateAppointment(@PathVariable @Valid @Min(1) long id,
                                                @RequestBody AppointmentNewDTO appointmentNewDTO) {
        return appointmentService.updateAppointment(id, appointmentNewDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AppointmentDataDTO deleteAppointment(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.deleteAppointment(id);
    }

    @PutMapping("/{id}")
    public AppointmentDataDTO closeAppointment(@PathVariable @Valid @Min(1) long id,
                                               @RequestParam @Valid @NotNull
                                               @Pattern(regexp = "^[\\w].{2,30}$",
                                                       message = "must contain at least 2 characters")
                                                       String diagnosis) {
        return appointmentService.closeAppointment(id, diagnosis);
    }

    @PatchMapping("/{id}/make")
    public AppointmentDataDTO makeAppointment(@PathVariable @Valid @Min(1) long id,
                                              @RequestBody AppointmentMakeDTO appointmentMakeDTO) {
        return appointmentService.makeAppointment(id, appointmentMakeDTO);
    }
    @PutMapping("/{id}/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AppointmentDataDTO returnAppointment(@PathVariable @Valid @Min(1) long id) {
        return appointmentService.returnAppointment(id);
    }
}
