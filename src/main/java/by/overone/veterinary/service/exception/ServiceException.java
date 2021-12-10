package by.overone.veterinary.service.exception;

import by.overone.veterinary.dao.exception.DaoException;


public class ServiceException extends Exception {

    public ServiceException(DaoException e) {
    }
}
