package by.overone.veterinary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

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
    @Min(5)
    private String reason;
    @Min(5)
    private String diagnosis;
    private String status;
}
