package by.overone.veterinary.model;

import lombok.*;

import javax.management.ConstructorParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Pet {
    private long id;
    @NonNull private String name;
    @NonNull private String type;
    @NonNull private String breed;
    @NonNull private int age;
    private String status;

}
