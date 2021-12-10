package by.overone.veterinary.dto;

import lombok.Data;

@Data
public class UserDataDTO {
    private long id;
    private String login;
    private String email;
    private String role;

}
