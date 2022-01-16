package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentActiveDTO {
    private long appointment_id;
    private Date date;
    private Time time;
    private long users_doctor_id;
    private long users_user_id;
    private long pet_id;
    private String reason;
}
