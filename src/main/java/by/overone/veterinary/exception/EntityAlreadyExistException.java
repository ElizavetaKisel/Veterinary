package by.overone.veterinary.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityAlreadyExistException extends RuntimeException{
    private ExceptionCode code;
    public EntityAlreadyExistException(ExceptionCode code) {
        this.code = code;
    }
}
