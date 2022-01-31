package by.overone.veterinary.model;

import by.overone.veterinary.validator.NewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private long id;
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of name", groups = {NewEntity.class})
    private String name;
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of surname", groups = {NewEntity.class})
    private String surname;
    private String address;
    @Pattern(regexp = "^(\\+375|80)(17|29|33|44)[0-9]{7}$", message = "Phone number is invalid", groups = {NewEntity.class})
    @Column(name = "phone_number")
    private String phoneNumber;
}
