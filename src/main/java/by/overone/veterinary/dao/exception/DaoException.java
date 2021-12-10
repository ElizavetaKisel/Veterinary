package by.overone.veterinary.dao.exception;

import java.sql.SQLException;

public class DaoException extends Exception{

    public DaoException(String exMessage, SQLException e) {
        super(exMessage, e);
    }
}