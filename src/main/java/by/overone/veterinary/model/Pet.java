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
}
