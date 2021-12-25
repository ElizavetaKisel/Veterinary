package by.overone.veterinary.model;

import lombok.*;

import javax.management.ConstructorParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private long id;
    private String name;
    private String type;
    private String breed;
    private int age;
    private String status;

}
