package by.overone.veterinary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

    private String login;
    private String email;
    private String role;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
}
