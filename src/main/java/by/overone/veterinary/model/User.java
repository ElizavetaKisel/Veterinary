package by.overone.veterinary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    private long id;
    @NonNull private String login;
    @NonNull private String password;
    @NonNull private String email;
    @NonNull private String role;
    private String status;

}
