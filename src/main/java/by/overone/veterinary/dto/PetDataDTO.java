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

//    public PetDataDTO(String name, String type, String breed, int age){
//        this.name = name;
//        this.type = type;
//        this.breed = breed;
//        this.age = age;
//    }
}
