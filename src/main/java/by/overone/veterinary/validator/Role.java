package by.overone.veterinary.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface Role {

    String message() default "Role is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
