package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentMakeDTO {

    @NotNull
    @NotBlank
    @Min(1)
    private long userId;
    @NotNull
    @NotBlank
    @Min(1)
    private long petId;
    private String reason;
}
