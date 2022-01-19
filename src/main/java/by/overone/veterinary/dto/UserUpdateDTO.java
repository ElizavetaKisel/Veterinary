package by.overone.veterinary.dto;

import by.overone.veterinary.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private long user_id;
    @NotBlank(message = "Login is blank")
    private String password;
    @NotBlank(message = "Email is blank")
    private String email;
    @NotBlank(message = "Role is blank")
    private String role;
}
