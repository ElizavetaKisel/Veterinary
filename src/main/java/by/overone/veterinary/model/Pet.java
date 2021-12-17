package by.overone.veterinary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private long id;
    private String name;
    private String type;
    private String breed;
    private int age;

    public Pet (String name, String type, String breed, int age){
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
    }
}
