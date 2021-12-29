package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private String name;
    private String surname;
    private String address;
    private String phone_number;
}
