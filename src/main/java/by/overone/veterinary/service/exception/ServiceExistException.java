package by.overone.veterinary.service.exception;

import by.overone.veterinary.dao.exception.DaoExistException;

public class ServiceExistException extends Exception{
    public ServiceExistException(String message, DaoExistException e) {
        super(message,e);
    }
}
