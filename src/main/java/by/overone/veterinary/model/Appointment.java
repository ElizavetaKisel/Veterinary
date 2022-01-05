package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private long appointment_id;
    private long users_doctor_id;
    private long users_user_id;
    private long pet_id;
    private String reason;
    private String diagnosis;
}
