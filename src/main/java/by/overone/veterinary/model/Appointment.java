package by.overone.veterinary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
//    @JsonFormat(pattern = "yyy-MM-dd")
//    private Date date;
//    @JsonFormat(pattern = "hh:mm")
//    private Time time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name="date_time", nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private User doctor;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="pet_id")
    private Pet pet;
    @Column(nullable = false)
    private String reason;
    private String diagnosis;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
