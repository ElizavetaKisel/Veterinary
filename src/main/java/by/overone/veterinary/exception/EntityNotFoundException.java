package by.overone.veterinary.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException {
    private ExceptionCode code;
    public EntityNotFoundException(ExceptionCode code) {
        this.code = code;
    }
}
