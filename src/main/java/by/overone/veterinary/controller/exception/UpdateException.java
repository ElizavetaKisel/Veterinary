package by.overone.veterinary.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateException extends RuntimeException{
    private ExceptionCode code;
    public UpdateException(ExceptionCode code) {
        this.code = code;
    }
}
