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
    @NotBlank
    @Pattern(regexp = "^[\\w]{4,12}$",
            message = "must contain from 4 to 12 characters")
    private String login;
    @NotNull(message = "Enter your password")
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,}$",
            message = "must contain at least 6 characters, including at least one capital letter and number")
    private String password;
    @NotNull(message = "Enter your email")
    @NotBlank
    @Pattern(regexp = "^[^\\s]+@[\\w]+\\.[a-z]+$", message = "Email is invalid")
    private String email;
}
