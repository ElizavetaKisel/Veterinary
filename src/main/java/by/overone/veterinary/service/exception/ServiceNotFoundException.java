package by.overone.veterinary.service.exception;

import by.overone.veterinary.dao.exception.DaoNotFoundException;

public class ServiceNotFoundException extends Exception{
    public ServiceNotFoundException(String message, DaoNotFoundException e) {
        super(message,e);
    }
}
