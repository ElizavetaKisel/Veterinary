package by.overone.veterinary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentNewDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")

    private LocalDateTime dateTime;
    private Long doctorId;
}
