package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDTO {

    private String login;
    private String password;
    private String email;
}
