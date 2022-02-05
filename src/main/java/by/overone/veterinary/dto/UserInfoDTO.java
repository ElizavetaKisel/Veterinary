package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private String login;
    private String email;
    private String role;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
}
