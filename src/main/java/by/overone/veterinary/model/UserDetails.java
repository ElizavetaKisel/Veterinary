package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class UserDetails {
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of name")
    private String name;
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of surname")
    private String surname;
    private String address;
    @Pattern(regexp = "^(\\+375|80)(17|29|33|44)[0-9]{7}$", message = "Phone number is invalid")
    private String phone_number;
    private long users_user_id;
}
