package by.overone.veterinary.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private long id;
    private String name;
    @Column(nullable = false)
    private String type;
    private String breed;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    @ManyToMany(mappedBy = "users")
    private List<User> owners;
    @OneToMany(mappedBy="appointments")
    private List<Appointment> appointments;

}
