package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotNull(message = "Enter your login")
    @NotBlank(message = "Login is blank")
    @Pattern(regexp = "^[\\w]{4,12}$", message = "Incorrect format of login")
    private String login;
    @NotNull(message = "Enter your password")
    @NotBlank(message = "Password is blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$", message = "Incorrect format of password")
    private String password;
    @NotNull(message = "Enter your email")
    @NotBlank(message = "Email is blank")
    @Pattern(regexp = "^[^\\s]+@[\\w]+\\.[a-z]+$", message = "Incorrect format of email")
    private String email;
}
