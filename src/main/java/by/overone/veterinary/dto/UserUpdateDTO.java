package by.overone.veterinary.dto;

import by.overone.veterinary.model.Role;
import by.overone.veterinary.validator.NewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @NotBlank(message = "Login is blank")
    private String password;
    @NotBlank(message = "Email is blank")
    private String email;
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of name", groups = {NewEntity.class})
    private String name;
    @Pattern(regexp = "^[A-ZА-Я].*$", message = "Incorrect format of surname", groups = {NewEntity.class})
    private String surname;
    private String address;
    @Pattern(regexp = "^(\\+375|80)(17|29|33|44)[0-9]{7}$", message = "Phone number is invalid", groups = {NewEntity.class})
    @Column(name = "phone_number")
    private String phoneNumber;
}
