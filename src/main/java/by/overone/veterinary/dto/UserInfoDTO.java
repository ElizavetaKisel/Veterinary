package by.overone.veterinary.dto;

import by.overone.veterinary.model.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private long id;
    private String login;
    private String email;
    private String role;
    private UserDetails userDetails;
}
