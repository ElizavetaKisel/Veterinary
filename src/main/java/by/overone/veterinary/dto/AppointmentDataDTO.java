package by.overone.veterinary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDataDTO {
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String dateTime;
    @Min(1)
    private Long doctorId;
    @Min(1)
    private Long userId;
    @Min(1)
    private Long petId;
    @Pattern(regexp = "^[\\w].{2,30}$", message = "must contain at least 2 characters")
    private String reason;
    @Pattern(regexp = "^[\\w].{2,30}$", message = "must contain at least 2 characters")
    private String diagnosis;
    private String status;
}
