package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PetDataDTO {

    private long id;
    private String name;
    private String type;
    private String breed;
    private int age;
}
