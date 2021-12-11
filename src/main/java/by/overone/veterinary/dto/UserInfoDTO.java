package by.overone.veterinary.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

    private String login;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
}
