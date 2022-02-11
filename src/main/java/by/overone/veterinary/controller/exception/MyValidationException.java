package by.overone.veterinary.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MyValidationException extends RuntimeException{
    private ExceptionCode code;
    public MyValidationException(ExceptionCode code) {
        this.code = code;
    }
}
