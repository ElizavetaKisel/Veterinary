package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private long appointment_id;
    private LocalDate date;
    private Time time;
    private long users_doctor_id;
    private long users_user_id;
    private long pet_id;
    private String reason;
    private String diagnosis;
}
