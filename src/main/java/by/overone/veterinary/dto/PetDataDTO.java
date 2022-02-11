package by.overone.veterinary.dto;


import by.overone.veterinary.validator.NewEntity;
import by.overone.veterinary.validator.UpdateEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDataDTO {

    @NotNull(groups = {NewEntity.class})
    @Pattern(regexp = "^[\\w].{2,20}$", message = "must contain at least 2 characters",
            groups = {NewEntity.class ,UpdateEntity.class})
    private String name;
    @NotNull(groups = {NewEntity.class})
    @Pattern(regexp = "^[\\w].{2,20}$", message = "must contain at least 2 characters",
            groups = {NewEntity.class ,UpdateEntity.class})
    private String type;
    @NotNull(groups = {NewEntity.class})
    @Pattern(regexp = "^[\\w].{2,20}$", message = "must contain at least 2 characters",
            groups = {NewEntity.class ,UpdateEntity.class})
    private String breed;
    @NotNull(groups = {NewEntity.class})
    @Min(value = 1, groups = {NewEntity.class ,UpdateEntity.class})
    private Integer age;
    private List<Long> owners;

}
