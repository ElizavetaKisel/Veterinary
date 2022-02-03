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

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$", message = "Incorrect format of password")
    private String password;
    @Pattern(regexp = "^[^\\s]+@[\\w]+\\.[a-z]+$", message = "Incorrect format of email")
    private String email;
    @Pattern(regexp = "^[A-Z].*$", message = "Incorrect format of name")
    private String name;
    @Pattern(regexp = "^[A-Z].*$", message = "Incorrect format of surname")
    private String surname;
    private String address;
    @Pattern(regexp = "^(\\+375|80)(17|29|33|44)[0-9]{7}$", message = "Phone number is invalid")
    @Column(name = "phone_number")
    private String phoneNumber;
}
