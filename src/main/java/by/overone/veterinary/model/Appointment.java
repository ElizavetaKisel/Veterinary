package by.overone.veterinary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long id;
    @JsonFormat(pattern = "yyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "hh:mm")
    private Time time;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private User doctor;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @ManyToOne
    @JoinColumn(name="pet_id", nullable=false)
    private Pet pet;
    private String reason;
    private String diagnosis;
}
