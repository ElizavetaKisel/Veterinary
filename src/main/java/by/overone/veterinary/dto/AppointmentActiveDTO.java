package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentActiveDTO {
    private long appointment_id;
    private long users_doctor_id;
    private long users_user_id;
    private long pet_id;
    private String reason;
}
