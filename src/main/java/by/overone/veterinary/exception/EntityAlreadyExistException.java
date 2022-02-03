package by.overone.veterinary.exception;

import lombok.Data;

@Data
public class EntityAlreadyExistException extends RuntimeException{
    private ExceptionCode code;
    public EntityAlreadyExistException(ExceptionCode code) {
        this.code = code;
    }
}
