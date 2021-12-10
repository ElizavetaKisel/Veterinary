package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
}
