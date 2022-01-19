package by.overone.veterinary.dto;


import by.overone.veterinary.validator.NewEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDataDTO {

    private long pet_id;
    private List<Long> user_id;
    @NotNull(groups = {NewEntity.class})
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @NotNull(groups = {NewEntity.class})
    @NotBlank
    @Size(min = 2, max = 20)
    private String type;
    @NotNull(groups = {NewEntity.class})
    @NotBlank
    @Size(min = 2, max = 20)
    private String breed;
    @NotNull(groups = {NewEntity.class})
    @NotBlank
    private Integer age;

}
