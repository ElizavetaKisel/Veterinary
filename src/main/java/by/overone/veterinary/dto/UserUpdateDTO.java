package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$",
            message = "must contain at least 6 characters, including at least one capital letter and number")
    private String oldPassword;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$",
            message = "must contain at least 6 characters, including at least one capital letter and number")
    private String password;
    @Pattern(regexp = "^[^\\s]+@[\\w]+\\.[a-z]+$", message = "is invalid")
    private String email;
    @Pattern(regexp = "^[A-Z].*$", message = "must start with a capital letter")
    private String name;
    @Pattern(regexp = "^[A-Z].*$", message = "must start with a capital letter")
    private String surname;
    @Pattern(regexp = "^[\\w].{2,30}$", message = "must contain at least 2 characters")
    private String address;
    @Pattern(regexp = "^(\\+375|80)(17|29|33|44)[0-9]{7}$", message = "is invalid")
    @Column(name = "phone_number")
    private String phoneNumber;
}
