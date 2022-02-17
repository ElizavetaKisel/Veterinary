package by.overone.veterinary.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pets")
public class   Pet {
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
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    @ManyToMany(cascade = { CascadeType.ALL }, fetch =  FetchType.EAGER)
    @JoinTable(
            name = "pets_has_users",
            joinColumns = { @JoinColumn(name = "pet_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> owners;

}
