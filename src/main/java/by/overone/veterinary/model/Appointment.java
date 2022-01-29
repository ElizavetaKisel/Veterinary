package by.overone.veterinary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private long appointment_id;
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "hh:mm")
    private Time time;
    private long users_doctor_id;
    private long users_user_id;
    private long pet_id;
    private String reason;
    private String diagnosis;
}
