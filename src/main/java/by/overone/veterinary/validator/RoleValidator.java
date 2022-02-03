package by.overone.veterinary.validator;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Transactional
public class RoleValidator implements ConstraintValidator<Role, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EnumUtils.isValidEnum(by.overone.veterinary.model.Role.class, value.toUpperCase());
    }

}
