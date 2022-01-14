package by.overone.veterinary.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public class EntityAlreadyExistException extends Exception{
    public EntityAlreadyExistException(String message, SQLIntegrityConstraintViolationException ex) {
        super(message, ex);
    }
}
