package by.overone.veterinary.dao.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public class DaoExistException extends Exception{
    public DaoExistException(String message, SQLIntegrityConstraintViolationException ex) {
        super(message, ex);
    }
}
