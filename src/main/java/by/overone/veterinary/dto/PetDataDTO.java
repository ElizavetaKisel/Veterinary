package by.overone.veterinary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDataDTO {

    private long pet_id;
    private List<Long> user_id;
    private String name;
    private String type;
    private String breed;
    private int age;

}
