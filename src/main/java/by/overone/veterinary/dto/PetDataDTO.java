package by.overone.veterinary.dto;

import by.overone.veterinary.validator.NewEntity;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDataDTO {

    private long pet_id;
    private List<Long> user_id;
//    @NotNull(groups = {NewEntity.class})
//    @Size(min = 3, max = 10, groups = {NewEntity.class, Update.class})
    private String name;
    private String type;
    private String breed;
    private int age;

}
