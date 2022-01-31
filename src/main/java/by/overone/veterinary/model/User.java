package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @JoinColumn(name = "detail_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "pets_has_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "pet_id") }
    )
    private List<Pet> pets;
    @OneToMany(mappedBy="appointments")
    private List<Appointment> appointments;

}
