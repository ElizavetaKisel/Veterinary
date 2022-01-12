package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDataDTO {

    private long pet_id;
    private String name;
    private String type;
    private String breed;
    private int age;

}
