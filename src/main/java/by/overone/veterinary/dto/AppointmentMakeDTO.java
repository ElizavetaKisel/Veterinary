package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentMakeDTO {

    @NotNull
    @Min(1)
    private long userId;
    @NotNull
    @Min(1)
    private long petId;
    @NotNull
    @Pattern(regexp = "^[\\w].{2,30}$", message = "must contain at least 2 characters")
    private String reason;
}
