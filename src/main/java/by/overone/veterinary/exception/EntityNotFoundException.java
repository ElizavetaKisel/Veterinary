package by.overone.veterinary.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityNotFoundException extends RuntimeException {
    private ExceptionCode code;
    public EntityNotFoundException(ExceptionCode code) {
        this.code = code;
    }
}
