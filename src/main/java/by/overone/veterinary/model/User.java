package by.overone.veterinary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private long user_id;
    private String login;
    private String password;
    private String email;
    private String role;
    private String status;

}
